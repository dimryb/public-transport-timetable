package space.rybakov.timetable.api.v2

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import space.rybakov.timetable.api.v2.models.Direction
import space.rybakov.timetable.api.v2.models.IResponse
import space.rybakov.timetable.api.v2.models.TripCreateResponse
import space.rybakov.timetable.api.v2.models.TripResponseObject
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response: IResponse = TripCreateResponse(
        responseType = "create",
        requestId = "123",
        trip = TripResponseObject(
            name = "trip name",
            description = "trip description",
            tripType = Direction.FORWARD,
        )
    )

    @Test
    fun serialize() {
        val json = apiV2Mapper.encodeToString(response)

        println(json)

        assertContains(json, Regex("\"name\":\\s*\"trip name\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(response)
        val obj = apiV2Mapper.decodeFromString(json) as TripCreateResponse

        assertEquals(response, obj)
    }
}
