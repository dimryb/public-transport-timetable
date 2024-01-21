package space.rybakov.timetable.app.repo

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import space.rybakov.timetable.api.v2.apiV2Mapper
import space.rybakov.timetable.api.v2.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class V2TripInmemoryApiTest {
    private val createTrip = TripCreateObject(
        name = "11а",
        description = "Маршрут 11а",
        tripType = Direction.FORWARD,
    )
    private val requestObj = TripCreateRequest(
        requestId = "12345",
        trip = createTrip,
        debug = TripDebug(
            mode = TripRequestDebugMode.TEST,
        )
    )
    @Test
    fun create() = testApplication {
        val responseObj = initObject(client)
        assertEquals(createTrip.name, responseObj.trip?.name)
        assertEquals(createTrip.description, responseObj.trip?.description)
        assertEquals(createTrip.tripType, responseObj.trip?.tripType)
    }

    @Test
    fun read() = testApplication {
        val tripCreateResponse = initObject(client)
        val oldId = tripCreateResponse.trip?.id
        val response = client.post("/v2/trip/read") {

            val requestObj = TripReadRequest(
                requestId = "12345",
                trip = TripReadObject(oldId),
                debug = TripDebug(
                    mode = TripRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<TripReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(oldId, responseObj.trip?.id)
    }

    @Test
    fun update() = testApplication {
        val initObject = initObject(client)
        val tripUpdate = TripUpdateObject(
            id = initObject.trip?.id,
            name = "11а",
            description = "Маршрут 11а",
            tripType = Direction.FORWARD,
            lock = initObject.trip?.lock,
        )

        val response = client.post("/v2/trip/update") {
            val requestObj = TripUpdateRequest(
                requestId = "12345",
                trip = tripUpdate,
                debug = TripDebug(
                    mode = TripRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<TripUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(tripUpdate.id, responseObj.trip?.id)
        assertEquals(tripUpdate.name, responseObj.trip?.name)
        assertEquals(tripUpdate.description, responseObj.trip?.description)
        assertEquals(tripUpdate.tripType, responseObj.trip?.tripType)
    }

    @Test
    fun delete() = testApplication {
        val initObject = initObject(client)
        val id = initObject.trip?.id
        val response = client.post("/v2/trip/delete") {
            val requestObj = TripDeleteRequest(
                requestId = "12345",
                trip = TripDeleteObject(
                    id = id,
                    lock = initObject.trip?.lock,
                ),
                debug = TripDebug(
                    mode = TripRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<TripDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(id, responseObj.trip?.id)
    }

    @Test
    fun search() = testApplication {
        val initObject = initObject(client)
        val response = client.post("/v2/trip/search") {
            val requestObj = TripSearchRequest(
                requestId = "12345",
                tripFilter = TripSearchFilter(),
                debug = TripDebug(
                    mode = TripRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV2Mapper.decodeFromString<TripSearchResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.trips?.size)
        assertEquals(initObject.trip?.id, responseObj.trips?.first()?.id)
    }
    private suspend fun initObject(client: HttpClient): TripCreateResponse {
        val response = client.post("/v2/trip/create") {
            contentType(ContentType.Application.Json)
            val requestJson = apiV2Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        assertEquals(200, response.status.value)
        return apiV2Mapper.decodeFromString<TripCreateResponse>(responseJson)
    }
}
