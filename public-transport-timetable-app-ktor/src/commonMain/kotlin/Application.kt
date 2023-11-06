package space.rybakov.timetable.app.ktor

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import space.rybakov.timetable.api.v2.apiV2Mapper
import space.rybakov.timetable.app.ktor.v2.v2Trip
import space.rybakov.timetable.biz.TimetableTripProcessor

fun Application.module(processor: TimetableTripProcessor = TimetableTripProcessor()) {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v2") {
            install(ContentNegotiation) {
                json(apiV2Mapper)
            }

            v2Trip(processor)
        }
    }
}

fun main() {
    embeddedServer(CIO, port = 8080) {
        module()
    }.start(wait = true)
}