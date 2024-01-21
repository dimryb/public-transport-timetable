package space.rybakov.timetable.common.models

data class TimetableTrip(
    var id: TimetableTripId = TimetableTripId.NONE,
    var name: String = "",
    var description: String = "",
    var ownerId: TimetableUserId = TimetableUserId.NONE,
    var tripType: TimetableDirection = TimetableDirection.NONE,
    var lock: TimetableTripLock = TimetableTripLock.NONE,
    val permissionsClient: MutableSet<TimetableTripPermissionClient> = mutableSetOf()
){
    fun deepCopy(): TimetableTrip = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )
}
