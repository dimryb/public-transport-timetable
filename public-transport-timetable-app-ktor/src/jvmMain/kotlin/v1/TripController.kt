package space.rybakov.timetable.app.ktor.v1

import io.ktor.server.application.*
import space.rybakov.timetable.api.v1.models.*
import space.rybakov.timetable.app.common.TimetableAppSettings
import space.rybakov.timetable.app.ktor.v1.processV1

suspend fun ApplicationCall.createTrip(appSettings: TimetableAppSettings) : Unit =
    processV1<TripCreateRequest, TripCreateResponse>(appSettings, ApplicationCall::createTrip::class, "trip-create")

suspend fun ApplicationCall.readTrip(appSettings: TimetableAppSettings) : Unit =
    processV1<TripReadRequest, TripReadResponse>(appSettings, ApplicationCall::readTrip::class, "trip-read")

suspend fun ApplicationCall.updateTrip(appSettings: TimetableAppSettings) : Unit =
    processV1<TripUpdateRequest, TripUpdateResponse>(appSettings, ApplicationCall::updateTrip::class, "trip-update")

suspend fun ApplicationCall.deleteTrip(appSettings: TimetableAppSettings) : Unit =
    processV1<TripDeleteRequest, TripDeleteResponse>(appSettings, ApplicationCall::deleteTrip::class, "trip-delete")

suspend fun ApplicationCall.searchTrip(appSettings: TimetableAppSettings) : Unit =
    processV1<TripSearchRequest, TripSearchResponse>(appSettings, ApplicationCall::searchTrip::class, "trip-search")