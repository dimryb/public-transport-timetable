package space.rybakov.timetable.app.ktor.v2

import io.ktor.server.application.*
import io.ktor.server.routing.*
import space.rybakov.timetable.app.common.TimetableAppSettings

fun Route.v2Trip(appSettings: TimetableAppSettings) {
    route("trip") {
        post("create") {
            call.createTrip(appSettings)
        }
        post("read") {
            call.readTrip(appSettings)
        }
        post("update") {
            call.updateTrip(appSettings)
        }
        post("delete") {
            call.deleteTrip(appSettings)
        }
        post("search") {
            call.searchTrip(appSettings)
        }
    }
}

