package space.rybakov.timetable.common.repo

import space.rybakov.timetable.common.models.TimetableTrip

data class DbTripRequest(
    val trip: TimetableTrip
)
