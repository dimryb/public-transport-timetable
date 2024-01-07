package space.rybakov.timetable.backend.repo.tests

import space.rybakov.timetable.common.models.TimetableDirection
import space.rybakov.timetable.common.models.TimetableTrip
import space.rybakov.timetable.common.models.TimetableTripId
import space.rybakov.timetable.common.models.TimetableUserId

abstract class BaseInitTrips(val op: String): IInitObjects<TimetableTrip> {

    fun createInitTestModel(
        suf: String,
        ownerId: TimetableUserId = TimetableUserId("owner-123"),
        tripType: TimetableDirection = TimetableDirection.FORWARD,
    ) = TimetableTrip(
        id = TimetableTripId("trip-repo-$op-$suf"),
        name = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        tripType = tripType,
    )
}
