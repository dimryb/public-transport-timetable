package space.rybakov.timetable.api.v2

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import space.rybakov.timetable.api.v2.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request: IRequest = TripCreateRequest(
        requestType = "create",
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
        val json = apiV2Mapper.encodeToString(request)

        println(json)

        assertContains(json, Regex("\"name\":\\s*\"trip name\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(request)
        val obj = apiV2Mapper.decodeFromString(json) as TripCreateRequest

        assertEquals(request, obj)
    }
    @Test
    fun deserializeNaked() {
        val jsonString = """
            {
            "requestType":"create",
            "requestId":"123",
            "debug":{"mode":"stub","stub":"badName"},
            "trip":{"name":"trip name","description":"trip description","tripType":"forward","productId":null}
            }
        """.trimIndent()
        val obj = apiV2RequestDeserialize(jsonString) as IRequest

        assertEquals("123", obj.requestId)
        assertEquals(request, obj)
    }
}
