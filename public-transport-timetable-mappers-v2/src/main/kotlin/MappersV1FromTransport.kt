package space.rybakov.timetable.mappers.v2

import space.rybakov.timetable.api.v1.models.*
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.stubs.TimetableStubs
import space.rybakov.timetable.mappers.v1.exceptions.UnknownRequestClass

fun TimetableContext.fromTransport(request: IRequest) = when (request) {
    is TripCreateRequest -> fromTransport(request)
    is TripReadRequest -> fromTransport(request)
    is TripUpdateRequest -> fromTransport(request)
    is TripDeleteRequest -> fromTransport(request)
    is TripSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toTripId() = this?.let { TimetableTripId(it) } ?: TimetableTripId.NONE
private fun String?.toTripWithId() = TimetableTrip(id = this.toTripId())
private fun IRequest?.requestId() = this?.requestId?.let { TimetableRequestId(it) } ?: TimetableRequestId.NONE

private fun TripDebug?.transportToWorkMode(): TimetableWorkMode = when (this?.mode) {
    TripRequestDebugMode.PROD -> TimetableWorkMode.PROD
    TripRequestDebugMode.TEST -> TimetableWorkMode.TEST
    TripRequestDebugMode.STUB -> TimetableWorkMode.STUB
    null -> TimetableWorkMode.PROD
}

private fun TripDebug?.transportToStubCase(): TimetableStubs = when (this?.stub) {
    TripRequestDebugStubs.SUCCESS -> TimetableStubs.SUCCESS
    TripRequestDebugStubs.NOT_FOUND -> TimetableStubs.NOT_FOUND
    TripRequestDebugStubs.BAD_ID -> TimetableStubs.BAD_ID
    TripRequestDebugStubs.BAD_NAME -> TimetableStubs.BAD_NAME
    TripRequestDebugStubs.BAD_DESCRIPTION -> TimetableStubs.BAD_DESCRIPTION
    TripRequestDebugStubs.CANNOT_DELETE -> TimetableStubs.CANNOT_DELETE
    TripRequestDebugStubs.BAD_SEARCH_STRING -> TimetableStubs.BAD_SEARCH_STRING
    null -> TimetableStubs.NONE
}

fun TimetableContext.fromTransport(request: TripCreateRequest) {
    command = TimetableCommand.CREATE
    requestId = request.requestId()
    tripRequest = request.trip?.toInternal() ?: TimetableTrip()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TimetableContext.fromTransport(request: TripReadRequest) {
    command = TimetableCommand.READ
    requestId = request.requestId()
    tripRequest = request.trip?.id.toTripWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TimetableContext.fromTransport(request: TripUpdateRequest) {
    command = TimetableCommand.UPDATE
    requestId = request.requestId()
    tripRequest = request.trip?.toInternal() ?: TimetableTrip()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TimetableContext.fromTransport(request: TripDeleteRequest) {
    command = TimetableCommand.DELETE
    requestId = request.requestId()
    tripRequest = request.trip?.id.toTripWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TimetableContext.fromTransport(request: TripSearchRequest) {
    command = TimetableCommand.SEARCH
    requestId = request.requestId()
    tripFilterRequest = request.tripFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TripSearchFilter?.toInternal(): TimetableTripFilter = TimetableTripFilter(
    searchString = this?.searchString ?: ""
)

private fun TripCreateObject.toInternal(): TimetableTrip = TimetableTrip(
    name = this.name ?: "",
    description = this.description ?: "",
    tripType = this.tripType.fromTransport(),
)

private fun TripUpdateObject.toInternal(): TimetableTrip = TimetableTrip(
    id = this.id.toTripId(),
    name = this.name ?: "",
    description = this.description ?: "",
    tripType = this.tripType.fromTransport(),
)

private fun Direction?.fromTransport(): TimetableDirection = when (this) {
    Direction.FORWARD -> TimetableDirection.FORWARD
    Direction.REVERSE -> TimetableDirection.REVERSE
    null -> TimetableDirection.NONE
}
