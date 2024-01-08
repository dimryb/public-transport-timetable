package space.rybakov.timetable.app.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import space.rybakov.timetable.api.v1.models.IRequest
import space.rybakov.timetable.api.v1.models.IResponse
import space.rybakov.timetable.app.common.TimetableAppSettings
import space.rybakov.timetable.app.common.process
import space.rybakov.timetable.mappers.v1.fromTransport
import space.rybakov.timetable.mappers.v1.toTransportTrip
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV1(
    appSettings: TimetableAppSettings,
    klass: KClass<*>,
    logId: String,
) {
    appSettings.processor.process(appSettings.logger.logger(klass), logId,
        fromTransport = {
            val request = receive<Q>()
            fromTransport(request)
        },
        sendResponse = { respond(toTransportTrip()) }
    )
}
