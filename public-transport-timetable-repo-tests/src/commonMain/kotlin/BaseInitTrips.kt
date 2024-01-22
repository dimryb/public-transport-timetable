package space.rybakov.timetable.backend.repo.tests

import space.rybakov.timetable.common.models.*

abstract class BaseInitTrips(val op: String): IInitObjects<TimetableTrip> {

    open val lockOld: TimetableTripLock = TimetableTripLock("20000000-0000-0000-0000-000000000002")
    open val lockBad: TimetableTripLock = TimetableTripLock("20000000-0000-0000-0000-000000000009")
    fun createInitTestModel(
        suf: String,
        ownerId: TimetableUserId = TimetableUserId("owner-123"),
        tripType: TimetableDirection = TimetableDirection.FORWARD,
        lock: TimetableTripLock = lockOld,
    ) = TimetableTrip(
        id = TimetableTripId("trip-repo-$op-$suf"),
        name = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        tripType = tripType,
        lock = lock,
    )
}
