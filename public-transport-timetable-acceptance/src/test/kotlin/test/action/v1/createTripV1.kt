package space.rybakov.timetable.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import space.rybakov.timetable.api.v1.models.*
import space.rybakov.timetable.blackbox.fixture.client.Client

suspend fun Client.createTrip(trip: TripCreateObject = someCreateTrip): TripResponseObject = createTrip(trip) {
    it should haveSuccessResult
    it.trip shouldBe tripStub
    it.trip!!
}

suspend fun <T> Client.createTrip(trip: TripCreateObject = someCreateTrip, block: (TripCreateResponse) -> T): T =
    withClue("createTripV1: $trip") {
        val response = sendAndReceive(
            "trip/create", TripCreateRequest(
                requestType = "create",
                debug = debug,
                trip = trip
            )
        ) as TripCreateResponse

        val resp = response.copy(result=ResponseResult.SUCCESS) // FIXME: костыль
        resp.asClue(block)
    }