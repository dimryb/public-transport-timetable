package space.rybakov.timetable.mappers.v1

import space.rybakov.timetable.api.v1.models.*
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.mappers.v1.exceptions.UnknownTimetableCommand

fun TimetableContext.toTransportTrip(): IResponse = when (val cmd = command) {
    TimetableCommand.CREATE -> toTransportCreate()
    TimetableCommand.READ -> toTransportRead()
    TimetableCommand.UPDATE -> toTransportUpdate()
    TimetableCommand.DELETE -> toTransportDelete()
    TimetableCommand.SEARCH -> toTransportSearch()
    TimetableCommand.NONE -> throw UnknownTimetableCommand(cmd)
}

fun TimetableContext.toTransportCreate() = TripCreateResponse(
    responseType = "create",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TimetableState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    trip = tripResponse.toTransportTrip()
)

fun TimetableContext.toTransportRead() = TripReadResponse(
    responseType = "read",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TimetableState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    trip = tripResponse.toTransportTrip()
)

fun TimetableContext.toTransportUpdate() = TripUpdateResponse(
    responseType = "update",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TimetableState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    trip = tripResponse.toTransportTrip()
)

fun TimetableContext.toTransportDelete() = TripDeleteResponse(
    responseType = "delete",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TimetableState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    trip = tripResponse.toTransportTrip()
)

fun TimetableContext.toTransportSearch() = TripSearchResponse(
    responseType = "search",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TimetableState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    trips = tripsResponse.toTransportTrip()
)

fun List<TimetableTrip>.toTransportTrip(): List<TripResponseObject>? = this
    .map { it.toTransportTrip() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun TimetableContext.toTransportInit() = TripInitResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

private fun TimetableTrip.toTransportTrip(): TripResponseObject = TripResponseObject(
    id = id.takeIf { it != TimetableTripId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != TimetableUserId.NONE }?.asString(),
    tripType = tripType.toTransportTrip(),
    permissions = permissionsClient.toTransportTrip(),
)

private fun Set<TimetableTripPermissionClient>.toTransportTrip(): Set<TripPermissions>? = this
    .map { it.toTransportTrip() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun TimetableTripPermissionClient.toTransportTrip() = when (this) {
    TimetableTripPermissionClient.READ -> TripPermissions.READ
    TimetableTripPermissionClient.UPDATE -> TripPermissions.UPDATE
    TimetableTripPermissionClient.DELETE -> TripPermissions.DELETE
}

private fun TimetableDirection.toTransportTrip(): Direction? = when (this) {
    TimetableDirection.FORWARD -> Direction.FORWARD
    TimetableDirection.REVERSE -> Direction.REVERSE
    TimetableDirection.NONE -> null
}

private fun List<TimetableError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportTrip() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun TimetableError.toTransportTrip() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)
