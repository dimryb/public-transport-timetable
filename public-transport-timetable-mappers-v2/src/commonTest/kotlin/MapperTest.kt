package ru.otus.otuskotlin.marketplace.mappers.v2

import space.rybakov.timetable.api.v2.models.*
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.stubs.TimetableStubs
import space.rybakov.timetable.mappers.v2.fromTransport
import space.rybakov.timetable.mappers.v2.toTransportTrip
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = TripCreateRequest(
            requestId = "1234",
            debug = TripDebug(
                mode = TripRequestDebugMode.STUB,
                stub = TripRequestDebugStubs.SUCCESS,
            ),
            trip = TripCreateObject(
                name = "name",
                description = "desc",
                tripType = Direction.FORWARD,
            ),
        )

        val context = TimetableContext()
        context.fromTransport(req)

        assertEquals(TimetableStubs.SUCCESS, context.stubCase)
        assertEquals(TimetableWorkMode.STUB, context.workMode)
        assertEquals("name", context.tripRequest.name)
        assertEquals(TimetableDirection.FORWARD, context.tripRequest.tripType)
    }

    @Test
    fun toTransport() {
        val context = TimetableContext(
            requestId = TimetableRequestId("1234"),
            command = TimetableCommand.CREATE,
            tripResponse = TimetableTrip(
                name = "name",
                description = "desc",
                tripType = TimetableDirection.FORWARD,
            ),
            errors = mutableListOf(
                TimetableError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = TimetableState.RUNNING,
        )

        val req = context.toTransportTrip() as TripCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("name", req.trip?.name)
        assertEquals("desc", req.trip?.description)
        assertEquals(Direction.FORWARD, req.trip?.tripType)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
