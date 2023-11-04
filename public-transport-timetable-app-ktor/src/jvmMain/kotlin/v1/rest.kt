package space.rybakov.timetable.app.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import space.rybakov.timetable.biz.TimetableTripProcessor

fun Route.v1Trip(processor: TimetableTripProcessor) {
    route("ad") {
        post("create") {
            call.createTrip(processor)
        }
        post("read") {
            call.readTrip(processor)
        }
        post("update") {
            call.updateTrip(processor)
        }
        post("delete") {
            call.deleteTrip(processor)
        }
        post("search") {
            call.searchTrip(processor)
        }
    }
}

