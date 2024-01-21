package space.rybakov.timetable.backend.repository.inmemory.model

import space.rybakov.timetable.common.models.*

data class TripEntity(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val tripType: String? = null,
    val lock: String? = null,
) {
    constructor(model: TimetableTrip): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        name = model.name.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        tripType = model.tripType.takeIf { it != TimetableDirection.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = TimetableTrip(
        id = id?.let { TimetableTripId(it) }?: TimetableTripId.NONE,
        name = name?: "",
        description = description?: "",
        ownerId = ownerId?.let { TimetableUserId(it) }?: TimetableUserId.NONE,
        tripType = tripType?.let { TimetableDirection.valueOf(it) }?: TimetableDirection.NONE,
        lock = lock?.let { TimetableTripLock(it) } ?: TimetableTripLock.NONE,
    )
}
