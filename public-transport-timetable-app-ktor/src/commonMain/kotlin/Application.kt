package space.rybakov.timetable.app.ktor

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.app.plugins.initPlugins
import space.rybakov.timetable.api.v2.apiV2Mapper
import space.rybakov.timetable.app.common.TimetableAppSettings
import space.rybakov.timetable.app.ktor.v2.v2Trip
import space.rybakov.timetable.app.plugins.initAppSettings

fun Application.module(appSettings: TimetableAppSettings = initAppSettings()) {
    initPlugins(appSettings)
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v2") {
            install(ContentNegotiation) {
                json(apiV2Mapper)
            }

            v2Trip(appSettings)
        }
    }
}

fun main() {
    embeddedServer(CIO, port = 8080, module = Application::module).start(wait = true)
}