package space.rybakov.timetable.common

import kotlinx.datetime.Instant
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.repo.ITripRepository
import space.rybakov.timetable.common.stubs.TimetableStubs

data class TimetableContext(
    var command: TimetableCommand = TimetableCommand.NONE,
    var state: TimetableState = TimetableState.NONE,
    val errors: MutableList<TimetableError> = mutableListOf(),
    var settings: TimetableCorSettings = TimetableCorSettings.NONE,

    var workMode: TimetableWorkMode = TimetableWorkMode.PROD,
    var stubCase: TimetableStubs = TimetableStubs.NONE,

    var tripRepo: ITripRepository = ITripRepository.NONE,
    var tripRepoRead: TimetableTrip = TimetableTrip(),
    var tripRepoPrepare: TimetableTrip = TimetableTrip(),
    var tripRepoDone: TimetableTrip = TimetableTrip(),
    var tripsRepoDone: MutableList<TimetableTrip> = mutableListOf(),

    var requestId: TimetableRequestId = TimetableRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var tripRequest: TimetableTrip = TimetableTrip(),
    var tripFilterRequest: TimetableTripFilter = TimetableTripFilter(),

    var tripValidating: TimetableTrip = TimetableTrip(),
    var tripFilterValidating: TimetableTripFilter = TimetableTripFilter(),

    var tripValidated: TimetableTrip = TimetableTrip(),
    var tripFilterValidated: TimetableTripFilter = TimetableTripFilter(),

    var tripResponse: TimetableTrip = TimetableTrip(),
    var tripsResponse: MutableList<TimetableTrip> = mutableListOf(),
)
