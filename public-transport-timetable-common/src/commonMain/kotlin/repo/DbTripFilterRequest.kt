package space.rybakov.timetable.common.repo

import space.rybakov.timetable.common.models.TimetableDirection
import space.rybakov.timetable.common.models.TimetableUserId

data class DbTripFilterRequest(
    val titleFilter: String = "",
    val ownerId: TimetableUserId = TimetableUserId.NONE,
    val direction: TimetableDirection = TimetableDirection.NONE,
)
