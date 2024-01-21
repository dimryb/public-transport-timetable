package space.rybakov.timetable.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import space.rybakov.timetable.backend.repository.inmemory.model.TripEntity
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
        val trip = rq.trip.copy(id = TimetableTripId(key))
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
        val newTrip = rq.trip.copy()
        val entity = TripEntity(newTrip)
        return when (cache.get(key)) {
            null -> resultErrorNotFound
            else -> {
                cache.put(key, entity)
                DbTripResponse(
                    data = newTrip,
                    isSuccess = true,
                )
            }
        }
    }

    override suspend fun deleteTrip(rq: DbTripIdRequest): DbTripResponse {
        val key = rq.id.takeIf { it != TimetableTripId.NONE }?.asString() ?: return resultErrorEmptyId
        return when (val oldTrip = cache.get(key)) {
            null -> resultErrorNotFound
            else -> {
                cache.invalidate(key)
                DbTripResponse(
                    data = oldTrip.toInternal(),
                    isSuccess = true,
                )
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
