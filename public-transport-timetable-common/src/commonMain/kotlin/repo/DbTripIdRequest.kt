package space.rybakov.timetable.common.repo

import space.rybakov.timetable.common.models.TimetableTrip
import space.rybakov.timetable.common.models.TimetableTripId

data class DbTripIdRequest(
    val id: TimetableTripId,
) {
    constructor(ad: TimetableTrip): this(ad.id)
}
