package space.rybakov.timetable.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import space.rybakov.timetable.backend.repository.inmemory.model.TripEntity
import space.rybakov.timetable.common.helpers.errorRepoConcurrency
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.repo.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class TripRepoInMemory(
    initObjects: List<TimetableTrip> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : ITripRepository {

    private val cache = Cache.Builder<String, TripEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(trip: TimetableTrip) {
        val entity = TripEntity(trip)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createTrip(rq: DbTripRequest): DbTripResponse {
        val key = randomUuid()
        val trip = rq.trip.copy(id = TimetableTripId(key), lock = TimetableTripLock(randomUuid()))
        val entity = TripEntity(trip)
        cache.put(key, entity)
        return DbTripResponse(
            data = trip,
            isSuccess = true,
        )
    }

    override suspend fun readTrip(rq: DbTripIdRequest): DbTripResponse {
        val key = rq.id.takeIf { it != TimetableTripId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbTripResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateTrip(rq: DbTripRequest): DbTripResponse {
        val key = rq.trip.id.takeIf { it != TimetableTripId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.trip.lock.takeIf { it != TimetableTripLock.NONE }?.asString() ?: return resultErrorEmptyLock
        val newTrip = rq.trip.copy(lock = TimetableTripLock(randomUuid()))
        val entity = TripEntity(newTrip)
        return mutex.withLock {
            val oldTrip = cache.get(key)
            when {
                oldTrip == null -> resultErrorNotFound
                oldTrip.lock != oldLock -> DbTripResponse(
                    data = oldTrip.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(TimetableTripLock(oldLock), oldTrip.lock?.let { TimetableTripLock(it) }))
                )

                else -> {
                    cache.put(key, entity)
                    DbTripResponse(
                        data = newTrip,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteTrip(rq: DbTripIdRequest): DbTripResponse {
        val key = rq.id.takeIf { it != TimetableTripId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it != TimetableTripLock.NONE }?.asString() ?: return resultErrorEmptyLock
        return mutex.withLock {
            val oldTrip = cache.get(key)
            when {
                oldTrip == null -> resultErrorNotFound
                oldTrip.lock != oldLock -> DbTripResponse(
                    data = oldTrip.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(TimetableTripLock(oldLock), oldTrip.lock?.let { TimetableTripLock(it) }))
                )

                else -> {
                    cache.invalidate(key)
                    DbTripResponse(
                        data = oldTrip.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun searchTrip(rq: DbTripFilterRequest): DbTripsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != TimetableUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.direction.takeIf { it != TimetableDirection.NONE }?.let {
                    it.name == entry.value.tripType
                } ?: true
            }
            .filter { entry ->
                rq.nameFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.name?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbTripsResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbTripResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                TimetableError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbTripResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                TimetableError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbTripResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                TimetableError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
