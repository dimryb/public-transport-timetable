package space.rybakov.timetable.app.stubs

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.Test
import space.rybakov.timetable.api.v2.apiV2Mapper
import space.rybakov.timetable.api.v2.models.*
import kotlin.test.assertEquals

class V2TripStubApiTest {

    @Test
    fun create() = testApplication {
        val response = client.post("/v2/trip/create") {
            val requestObj = TripCreateRequest(
                requestId = "12345",
                trip = TripCreateObject(
                    name = "11",
                    description = "Маршрут 11",
                    tripType = Direction.FORWARD,
                ),
                debug = TripDebug(
                    mode = TripRequestDebugMode.STUB,
                    stub = TripRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<TripCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.trip?.id)
    }

    @Test
    fun read() = testApplication {
        val response = client.post("/v2/trip/read") {
            val requestObj = TripReadRequest(
                requestId = "12345",
                trip = TripReadObject("666"),
                debug = TripDebug(
                    mode = TripRequestDebugMode.STUB,
                    stub = TripRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<TripReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.trip?.id)
    }

    @Test
    fun update() = testApplication {
        val response = client.post("/v2/trip/update") {
            val requestObj = TripUpdateRequest(
                requestId = "12345",
                trip = TripUpdateObject(
                    id = "666",
                    name = "11",
                    description = "Маршрут 11",
                    tripType = Direction.FORWARD,
                ),
                debug = TripDebug(
                    mode = TripRequestDebugMode.STUB,
                    stub = TripRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<TripUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.trip?.id)
    }

    @Test
    fun delete() = testApplication {
        val response = client.post("/v2/trip/delete") {
            val requestObj = TripDeleteRequest(
                requestId = "12345",
                trip = TripDeleteObject(
                    id = "666",
                    lock = "123"
                ),
                debug = TripDebug(
                    mode = TripRequestDebugMode.STUB,
                    stub = TripRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<TripDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.trip?.id)
    }

    @Test
    fun search() = testApplication {
        val response = client.post("/v2/trip/search") {
            val requestObj = TripSearchRequest(
                requestId = "12345",
                debug = TripDebug(
                    mode = TripRequestDebugMode.STUB,
                    stub = TripRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<TripSearchResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.trips?.first()?.id)
    }
}
