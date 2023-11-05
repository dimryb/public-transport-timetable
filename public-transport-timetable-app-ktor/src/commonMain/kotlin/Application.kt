package space.rybakov.timetable.app.ktor

import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import space.rybakov.timetable.api.v1.models.apiV1Mapper
import space.rybakov.timetable.app.ktor.v1.v1Trip
import space.rybakov.timetable.biz.TimetableTripProcessor

fun Application.module(processor: TimetableTripProcessor = TimetableTripProcessor()) {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v1") {
            install(ContentNegotiation) {
                jackson {
                    setConfig(apiV1Mapper.serializationConfig)
                    setConfig(apiV1Mapper.deserializationConfig)
                }
            }

            v1Trip(processor)
        }
    }
}

fun main() {
    embeddedServer(CIO, port = 8080) {
        module()
    }.start(wait = true)
}