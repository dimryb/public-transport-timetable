package space.rybakov.timetable.app.ktor.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import space.rybakov.timetable.api.v2.models.IRequest
import space.rybakov.timetable.api.v2.models.IResponse
import space.rybakov.timetable.app.common.TimetableAppSettings
import space.rybakov.timetable.app.common.process
import space.rybakov.timetable.mappers.v2.fromTransport
import space.rybakov.timetable.mappers.v2.toTransportTrip
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV2(
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
