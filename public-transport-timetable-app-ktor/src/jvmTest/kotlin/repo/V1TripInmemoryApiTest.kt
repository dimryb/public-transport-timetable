package space.rybakov.timetable.app.repo

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import space.rybakov.timetable.api.v1.models.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
class V1TripInmemoryApiTest {
    private val createTrip = TripCreateObject(
        name = "11a",
        description = "Маршрут 11а",
        tripType = Direction.FORWARD,
    )
    private val requestCreateObj = TripCreateRequest(
        requestId = "12345",
        trip = createTrip,
        debug = TripDebug(
            mode = TripRequestDebugMode.TEST,
        )
    )

    @Test
    fun create() = testApplication {
        val client = myClient()
        val responseObj = initObject(client)
        assertEquals(createTrip.name, responseObj.trip?.name)
        assertEquals(createTrip.description, responseObj.trip?.description)
        assertEquals(createTrip.tripType, responseObj.trip?.tripType)
    }

    @Test
    fun read() = testApplication {
        val client = myClient()
        val id = initObject(client).trip?.id
        val response = client.post("/v1/trip/read") {
            val requestObj = TripReadRequest(
                requestId = "12345",
                trip = TripReadObject(id),
                debug = TripDebug(
                    mode = TripRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<TripReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals(id, responseObj.trip?.id)
    }

    @Test
    fun update() = testApplication {
        val client = myClient()

        val created = initObject(client)

        val tripUpdate = TripUpdateObject(
            id = created.trip?.id,
            name = "11a",
            description = "Маршрут 11a",
            tripType = Direction.FORWARD,
            lock = created.trip?.lock,
        )

        val response = client.post("/v1/trip/update") {
            val requestObj = TripUpdateRequest(
                requestId = "12345",
                trip = tripUpdate,
                debug = TripDebug(
                    mode = TripRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<TripUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(tripUpdate.id, responseObj.trip?.id)
        assertEquals(tripUpdate.name, responseObj.trip?.name)
        assertEquals(tripUpdate.description, responseObj.trip?.description)
        assertEquals(tripUpdate.tripType, responseObj.trip?.tripType)
    }

    @Test
    fun delete() = testApplication {
        val client = myClient()
        val created = initObject(client)

        val response = client.post("/v1/trip/delete") {
            val requestObj = TripDeleteRequest(
                requestId = "12345",
                trip = TripDeleteObject(
                    id = created.trip?.id,
                    lock = created.trip?.lock
                ),
                debug = TripDebug(
                    mode = TripRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<TripDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals(created.trip?.id, responseObj.trip?.id)
    }

    @Test
    fun search() = testApplication {
        val client = myClient()
        val initObject = initObject(client)
        val response = client.post("/v1/trip/search") {
            val requestObj = TripSearchRequest(
                requestId = "12345",
                tripFilter = TripSearchFilter(),
                debug = TripDebug(
                    mode = TripRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<TripSearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.trips?.size)
        assertEquals(initObject.trip?.id, responseObj.trips?.first()?.id)
    }

    private suspend fun initObject(client: HttpClient): TripCreateResponse {
        val responseCreate = client.post("/v1/trip/create") {
            contentType(ContentType.Application.Json)
            setBody(requestCreateObj)
        }
        assertEquals(200, responseCreate.status.value)
        return responseCreate.body<TripCreateResponse>()
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
