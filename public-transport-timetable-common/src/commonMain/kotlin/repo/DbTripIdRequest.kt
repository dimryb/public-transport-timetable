package space.rybakov.timetable.common.repo

import space.rybakov.timetable.common.models.TimetableTrip
import space.rybakov.timetable.common.models.TimetableTripId
import space.rybakov.timetable.common.models.TimetableTripLock

data class DbTripIdRequest(
    val id: TimetableTripId,
    val lock: TimetableTripLock = TimetableTripLock.NONE,
) {
    constructor(ad: TimetableTrip): this(ad.id, ad.lock)
}
