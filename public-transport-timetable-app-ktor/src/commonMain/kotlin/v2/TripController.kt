package space.rybakov.timetable.app.ktor.v2

import io.ktor.server.application.*
import space.rybakov.timetable.api.v2.models.*
import space.rybakov.timetable.app.common.TimetableAppSettings

suspend fun ApplicationCall.createTrip(appSettings: TimetableAppSettings) : Unit =
    processV2<TripCreateRequest, TripCreateResponse>(appSettings, ApplicationCall::createTrip::class, "trip-create")

suspend fun ApplicationCall.readTrip(appSettings: TimetableAppSettings) : Unit =
    processV2<TripReadRequest, TripReadResponse>(appSettings, ApplicationCall::readTrip::class, "trip-read")

suspend fun ApplicationCall.updateTrip(appSettings: TimetableAppSettings) : Unit =
    processV2<TripUpdateRequest, TripUpdateResponse>(appSettings, ApplicationCall::updateTrip::class, "trip-update")

suspend fun ApplicationCall.deleteTrip(appSettings: TimetableAppSettings) : Unit =
    processV2<TripDeleteRequest, TripDeleteResponse>(appSettings, ApplicationCall::deleteTrip::class, "trip-delete")

suspend fun ApplicationCall.searchTrip(appSettings: TimetableAppSettings) : Unit =
    processV2<TripSearchRequest, TripSearchResponse>(appSettings, ApplicationCall::searchTrip::class, "trip-search")
