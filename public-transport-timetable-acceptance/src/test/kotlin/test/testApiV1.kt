package space.rybakov.timetable.blackbox.test

import io.kotest.assertions.asClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import space.rybakov.timetable.blackbox.fixture.client.Client
import space.rybakov.timetable.blackbox.test.action.v1.createTrip
import space.rybakov.timetable.blackbox.test.action.v1.readTrip

fun FunSpec.testApiV1(client: Client) {
    context("v1") {
        test("Create Trip ok") {
            client.createTrip()
        }

        test("Read Trip ok") {
            val created = client.createTrip()
            client.readTrip(created.id).asClue {
                it shouldBe created
            }
        }
    }
}