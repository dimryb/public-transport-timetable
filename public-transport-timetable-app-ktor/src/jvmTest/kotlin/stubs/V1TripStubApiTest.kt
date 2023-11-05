package space.rybakov.timetable.app.stubs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import space.rybakov.timetable.api.v1.models.*
import kotlin.test.assertEquals

class V1TripStubApiTest {
    @Test
    fun create() = testApplication {
        val client = myClient()

        val response = client.post("/v1/trip/create") {
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
            setBody(requestObj)
        }
        val responseObj = response.body<TripCreateResponse>()
        println(responseObj)
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.trip?.id)
    }

    @Test
    fun read() = testApplication {
        val client = myClient()

        val response = client.post("/v1/trip/read") {
            val requestObj = TripReadRequest(
                requestId = "12345",
                trip = TripReadObject("666"),
                debug = TripDebug(
                    mode = TripRequestDebugMode.STUB,
                    stub = TripRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<TripReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.trip?.id)
    }

    @Test
    fun update() = testApplication {
        val client = myClient()

        val response = client.post("/v1/trip/update") {
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
            setBody(requestObj)
        }
        val responseObj = response.body<TripUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.trip?.id)
    }

    @Test
    fun delete() = testApplication {
        val client = myClient()

        val response = client.post("/v1/trip/delete") {
            val requestObj = TripDeleteRequest(
                requestId = "12345",
                trip = TripDeleteObject(
                    id = "666",
                ),
                debug = TripDebug(
                    mode = TripRequestDebugMode.STUB,
                    stub = TripRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<TripDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.trip?.id)
    }

    @Test
    fun search() = testApplication {
        val client = myClient()

        val response = client.post("/v1/trip/search") {
            val requestObj = TripSearchRequest(
                requestId = "12345",
                tripFilter = TripSearchFilter(),
                debug = TripDebug(
                    mode = TripRequestDebugMode.STUB,
                    stub = TripRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<TripSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.trips?.first()?.id)
    }

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }
}
