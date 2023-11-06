package space.rybakov.timetable.app.ktor.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import space.rybakov.timetable.api.v2.models.*
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.mappers.v2.*

suspend fun ApplicationCall.createTrip(processor: TimetableTripProcessor) {
    val request = receive<TripCreateRequest>()
    val context = TimetableContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readTrip(processor: TimetableTripProcessor) {
    val request = receive<TripReadRequest>()
    val context = TimetableContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateTrip(processor: TimetableTripProcessor) {
    val request = receive<TripUpdateRequest>()
    val context = TimetableContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteTrip(processor: TimetableTripProcessor) {
    val request = receive<TripDeleteRequest>()
    val context = TimetableContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.searchTrip(processor: TimetableTripProcessor) {
    val request = receive<TripSearchRequest>()
    val context = TimetableContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportSearch())
}
