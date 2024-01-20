package space.rybakov.timetable.api.logs.mapper

import kotlinx.datetime.Clock
import space.rybakov.timetable.api.logs.models.*
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.*

fun TimetableContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "public-transport-timetable",
    trip = toTimetableLog(),
    errors = errors.map { it.toLog() },
)

fun TimetableContext.toTimetableLog(): TimetableLogModel? {
    val tripNone = TimetableTrip()
    return TimetableLogModel(
        requestId = requestId.takeIf { it != TimetableRequestId.NONE }?.asString(),
        requestTrip = tripRequest.takeIf { it != tripNone }?.toLog(),
        responseTrip = tripResponse.takeIf { it != tripNone }?.toLog(),
        responseTrips = tripsResponse.takeIf { it.isNotEmpty() }?.filter { it != tripNone }?.map { it.toLog() },
        requestFilter = tripFilterRequest.takeIf { it != TimetableTripFilter() }?.toLog(),
    ).takeIf { it != TimetableLogModel() }
}

private fun TimetableTripFilter.toLog() = TripFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != TimetableUserId.NONE }?.asString(),
    direction = direction.takeIf { it != TimetableDirection.NONE }?.name,
)

fun TimetableError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun TimetableTrip.toLog() = TripLog(
    id = id.takeIf { it != TimetableTripId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    tripType = tripType.takeIf { it != TimetableDirection.NONE }?.name,
    ownerId = ownerId.takeIf { it != TimetableUserId.NONE }?.asString(),
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)
