package space.rybakov.timetable.app.ktor

import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.event.Level
import space.rybakov.timetable.api.v1.models.apiV1Mapper
import space.rybakov.timetable.app.common.TimetableAppSettings
import space.rybakov.timetable.app.ktor.v1.v1Trip
import space.rybakov.timetable.app.plugins.initAppSettings
import space.rybakov.timetable.app.ktor.module as commonModule

// function with config (application.conf)
fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.moduleJvm(appSettings: TimetableAppSettings = initAppSettings()) {

    commonModule(appSettings)

    install(CallLogging) {
        level = Level.INFO
    }

    @Suppress("OPT_IN_USAGE")
    install(Locations)

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

            v1Trip(appSettings)
        }

        static("static") {
            resources("static")
        }
    }
}
