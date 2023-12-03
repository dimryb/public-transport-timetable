package space.rybakov.timetable.blackbox.test.action.v1

import io.kotest.assertions.withClue
import io.kotest.matchers.should
import space.rybakov.timetable.blackbox.fixture.client.Client

suspend fun Client.createTrip(): Unit =
    withClue("createTripV1") {
        val response = sendAndReceive(
            "trip/create", """
                {
                    "name": "11"
                }
            """.trimIndent()
        )

        response should haveNoErrors
    }
