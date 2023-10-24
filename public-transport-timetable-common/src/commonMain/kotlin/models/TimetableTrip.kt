package space.rybakov.timetable.common.models

data class TimetableTrip(
    var id: TimetableTripId = TimetableTripId.NONE,
    var title: String = "",
    var description: String = "",
    var ownerId: TimetableUserId = TimetableUserId.NONE,
    var adType: TimetableDealSide = TimetableDealSide.NONE,
    var visibility: TimetableVisibility = TimetableVisibility.NONE,
    val permissionsClient: MutableSet<TimetableTripPermissionClient> = mutableSetOf()
)
