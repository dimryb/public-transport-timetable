package space.rybakov.timetable.api.v1.models

import space.rybakov.timetable.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = TripCreateResponse(
        requestId = "123",
        trip = TripResponseObject(
            name = "trip name",
            description = "trip description",
            tripType = Direction.FORWARD,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"name\":\\s*\"trip name\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as TripCreateResponse

        assertEquals(response, obj)
    }
}
