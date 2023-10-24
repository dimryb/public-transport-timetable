package space.rybakov.timetable.common.models

data class TimetableTripFilter(
    var searchString: String = "",
    var ownerId: TimetableUserId = TimetableUserId.NONE,
    var dealSide: TimetableDirection = TimetableDirection.NONE,
)
