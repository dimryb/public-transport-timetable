package space.rybakov.timetable.common

import kotlinx.datetime.Instant
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.stubs.TimetableStubs

data class TimetableContext(
    var command: TimetableCommand = TimetableCommand.NONE,
    var state: TimetableState = TimetableState.NONE,
    val errors: MutableList<TimetableError> = mutableListOf(),

    var workMode: TimetableWorkMode = TimetableWorkMode.PROD,
    var stubCase: TimetableStubs = TimetableStubs.NONE,

    var requestId: TimetableRequestId = TimetableRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var tripRequest: TimetableTrip = TimetableTrip(),
    var tripFilterRequest: TimetableTripFilter = TimetableTripFilter(),
    var tripResponse: TimetableTrip = TimetableTrip(),
    var tripsResponse: MutableList<TimetableTrip> = mutableListOf(),
)
