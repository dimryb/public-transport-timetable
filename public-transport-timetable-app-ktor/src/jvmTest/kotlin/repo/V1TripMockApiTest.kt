package space.rybakov.timetable.app.repo

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import org.junit.Test
import space.rybakov.timetable.api.v1.models.*
import space.rybakov.timetable.app.common.TimetableAppSettings
import space.rybakov.timetable.app.ktor.moduleJvm
import space.rybakov.timetable.backend.repo.tests.TripRepositoryMock
import space.rybakov.timetable.common.TimetableCorSettings
import space.rybakov.timetable.common.models.TimetableTrip
import space.rybakov.timetable.common.repo.DbTripResponse
import space.rybakov.timetable.common.repo.DbTripsResponse
import space.rybakov.timetable.common.repo.ITripRepository
import space.rybakov.timetable.stubs.TimetableTripStub
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class V1TripMockApiTest {
    private val stub = TimetableTripStub.get()
    private val userId = stub.ownerId
    private val tripId = stub.id

    @Test
    fun create() = testApplication {
        initRepoTest(TripRepositoryMock(
            invokeCreateTrip = {
                DbTripResponse(
                    isSuccess = true,
                    data = it.trip.copy(id = tripId),
                )
            }
        ))

        val client = myClient()

        val createTrip = TripCreateObject(
            name = "11а",
            description = "Маршрут 11а",
            tripType = Direction.FORWARD,
        )

        val response = client.post("/v1/trip/create") {
            val requestObj = TripCreateRequest(
                requestId = "12345",
                trip = createTrip,
                debug = TripDebug(
                    mode = TripRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<TripCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(tripId.asString(), responseObj.trip?.id)
        assertEquals(createTrip.name, responseObj.trip?.name)
        assertEquals(createTrip.description, responseObj.trip?.description)
        assertEquals(createTrip.tripType, responseObj.trip?.tripType)
    }

    @Test
    fun read() = testApplication {
        initRepoTest(TripRepositoryMock(
            invokeReadTrip = {
                DbTripResponse(
                    isSuccess = true,
                    data = TimetableTrip(
                        id = it.id,
                        ownerId = userId,
                    ),
                )
            }
        ))

        val client = myClient()

        val response = client.post("/v1/trip/read") {
            val requestObj = TripReadRequest(
                requestId = "12345",
                trip = TripReadObject(tripId.asString()),
                debug = TripDebug(
                    mode = TripRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<TripReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals(tripId.asString(), responseObj.trip?.id)
    }

    @Test
    fun update() = testApplication {
        initRepoTest(TripRepositoryMock(
            invokeReadTrip = {
                DbTripResponse(
                    isSuccess = true,
                    data = TimetableTrip(
                        id = it.id,
                        ownerId = userId,
                    ),
                )
            },
            invokeUpdateTrip = {
                DbTripResponse(
                    isSuccess = true,
                    data = it.trip.copy(ownerId = userId),
                )
            }
        ))

        val client = myClient()

        val tripUpdate = TripUpdateObject(
            id = "666",
            name = "11а",
            description = "Маршрут 11а",
            tripType = Direction.FORWARD,
            lock = "123",
        )

        val response = client.post("/v1/trip/update") {
            val requestObj = TripUpdateRequest(
                requestId = "12345",
                trip = TripUpdateObject(
                    id = "666",
                    name = "11а",
                    description = "Маршрут 11а",
                    tripType = Direction.FORWARD,
                    lock = "123",
                ),
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
        initRepoTest(TripRepositoryMock(
            invokeReadTrip = {
                DbTripResponse(
                    isSuccess = true,
                    data = TimetableTrip(
                        id = it.id,
                        ownerId = userId,
                    ),
                )
            },
            invokeDeleteTrip = {
                DbTripResponse(
                    isSuccess = true,
                    data = TimetableTrip(
                        id = it.id,
                        ownerId = userId,
                    ),
                )
            }
        ))

        val client = myClient()

        val deleteId = "666"

        val response = client.post("/v1/trip/delete") {
            val requestObj = TripDeleteRequest(
                requestId = "12345",
                trip = TripDeleteObject(
                    id = deleteId,
                    lock = "123",
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
        assertEquals(deleteId, responseObj.trip?.id)
    }

    @Test
    fun search() = testApplication {
        initRepoTest(TripRepositoryMock(
            invokeSearchTrip = {
                DbTripsResponse(
                    isSuccess = true,
                    data = listOf(
                        TimetableTrip(
                            name = it.nameFilter,
                            ownerId = it.ownerId,
                            tripType = it.direction,
                        )
                    ),
                )
            }
        ))
        val client = myClient()

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

    private fun ApplicationTestBuilder.initRepoTest(repo: ITripRepository) {
        environment {
            config = MapApplicationConfig()
        }
        application {
            moduleJvm(TimetableAppSettings(corSettings = TimetableCorSettings(repoTest = repo)))
        }
    }
}
