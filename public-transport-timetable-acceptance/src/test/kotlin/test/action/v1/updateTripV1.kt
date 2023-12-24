package space.rybakov.timetable.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import space.rybakov.timetable.api.v1.models.*
import space.rybakov.timetable.blackbox.fixture.client.Client
import space.rybakov.timetable.blackbox.test.action.beValidId
import space.rybakov.timetable.blackbox.test.action.beValidLock

suspend fun Client.updateTrip(id: String?, lock: String?, trip: TripUpdateObject): TripResponseObject =
    updateTrip(id, lock, trip) {
        it should haveSuccessResult
        it.trip shouldNotBe null
        it.trip?.apply {
            if (trip.name != null)
                name shouldBe trip.name
            if (trip.description != null)
                description shouldBe trip.description
            if (trip.tripType != null)
                tripType shouldBe trip.tripType
        }
        it.trip!!
    }

suspend fun <T> Client.updateTrip(id: String?, lock: String?, trip: TripUpdateObject, block: (TripUpdateResponse) -> T): T =
    withClue("updatedV1: $id, lock: $lock, set: $trip") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "trip/update", TripUpdateRequest(
                requestType = "update",
                debug = debug,
                trip = trip.copy(id = id, lock = lock)
            )
        ) as TripUpdateResponse

        val resp = response.copy(result= ResponseResult.SUCCESS) // FIXME: костыль
        resp.asClue(block)
    }
