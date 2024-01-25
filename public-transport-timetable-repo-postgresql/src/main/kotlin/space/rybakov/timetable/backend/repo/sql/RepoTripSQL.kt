package space.rybakov.timetable.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import space.rybakov.timetable.common.helpers.asTimetableError
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.repo.*

class RepoTripSQL(
    properties: SqlProperties,
    initObjects: Collection<TimetableTrip> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : ITripRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
        }

        Database.connect(
            properties.url, driver, properties.user, properties.password
        )

        transaction {
            if (properties.dropDatabase) SchemaUtils.drop(TripTable)
            SchemaUtils.create(TripTable)
            initObjects.forEach { createTrip(it) }
        }
    }

    private fun createTrip(ad: TimetableTrip): TimetableTrip {
        val res = TripTable.insert {
            to(it, ad, randomUuid)
        }

        return TripTable.from(res)
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbTripResponse): DbTripResponse =
        transactionWrapper(block) { DbTripResponse.error(it.asTimetableError()) }

    override suspend fun createTrip(rq: DbTripRequest): DbTripResponse = transactionWrapper {
        DbTripResponse.success(createTrip(rq.trip))
    }

    private fun read(id: TimetableTripId): DbTripResponse {
        val res = TripTable.select {
            TripTable.id eq id.asString()
        }.singleOrNull() ?: return DbTripResponse.errorNotFound
        return DbTripResponse.success(TripTable.from(res))
    }

    override suspend fun readTrip(rq: DbTripIdRequest): DbTripResponse = transactionWrapper { read(rq.id) }

    private fun update(
        id: TimetableTripId,
        lock: TimetableTripLock,
        block: (TimetableTrip) -> DbTripResponse
    ): DbTripResponse =
        transactionWrapper {
            if (id == TimetableTripId.NONE) return@transactionWrapper DbTripResponse.errorEmptyId

            val current = TripTable.select { TripTable.id eq id.asString() }
                .firstOrNull()
                ?.let { TripTable.from(it) }

            when {
                current == null -> DbTripResponse.errorNotFound
                current.lock != lock -> DbTripResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    override suspend fun updateTrip(rq: DbTripRequest): DbTripResponse =
        update(rq.trip.id, rq.trip.lock) {
            TripTable.update({
                (TripTable.id eq rq.trip.id.asString()) and (TripTable.lock eq rq.trip.lock.asString())
            }) {
                to(it, rq.trip, randomUuid)
            }
            read(rq.trip.id)
        }

    override suspend fun deleteTrip(rq: DbTripIdRequest): DbTripResponse = update(rq.id, rq.lock) {
        TripTable.deleteWhere {
            (TripTable.id eq rq.id.asString()) and (TripTable.lock eq rq.lock.asString())
        }
        DbTripResponse.success(it)
    }

    override suspend fun searchTrip(rq: DbTripFilterRequest): DbTripsResponse =
        transactionWrapper({
            val res = TripTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != TimetableUserId.NONE) {
                        add(TripTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.direction != TimetableDirection.NONE) {
                        add(TripTable.direction eq rq.direction)
                    }
                    if (rq.nameFilter.isNotBlank()) {
                        add(
                            (TripTable.name like "%${rq.nameFilter}%")
                                or (TripTable.description like "%${rq.nameFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbTripsResponse(data = res.map { TripTable.from(it) }, isSuccess = true)
        }, {
            DbTripsResponse.error(it.asTimetableError())
        })
}
