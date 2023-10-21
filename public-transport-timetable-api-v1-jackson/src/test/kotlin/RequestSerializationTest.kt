package space.rybakov.timetable.api.v1.models

import space.rybakov.timetable.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = TripCreateRequest(
        requestId = "123",
        debug = TripDebug(
            mode = TripRequestDebugMode.STUB,
            stub = TripRequestDebugStubs.BAD_NAME
        ),
        trip = TripCreateObject(
            name = "trip name",
            description = "trip description",
            tripType = Direction.FORWARD,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"name\":\\s*\"trip name\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as TripCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, TripCreateRequest::class.java)

        assertEquals("123", obj.requestId)
    }
}
