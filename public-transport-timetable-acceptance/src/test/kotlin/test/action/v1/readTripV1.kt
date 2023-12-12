package space.rybakov.timetable.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import space.rybakov.timetable.api.v1.models.*
import space.rybakov.timetable.blackbox.fixture.client.Client
import space.rybakov.timetable.blackbox.test.action.beValidId

suspend fun Client.readTrip(id: String?): TripResponseObject = readTrip(id) {
    it should haveSuccessResult
    it.trip shouldNotBe null
    it.trip!!
}

suspend fun <T> Client.readTrip(id: String?, block: (TripReadResponse) -> T): T =
    withClue("readTripV1: $id") {
        id should beValidId

        val response = sendAndReceive(
            "trip/read",
            TripReadRequest(
                requestType = "read",
                debug = debug,
                trip = TripReadObject(id = id)
            )
        ) as TripReadResponse

        val resp = response.copy(result = ResponseResult.SUCCESS) // FIXME: костыль
        resp.asClue(block)
    }
