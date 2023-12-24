package space.rybakov.timetable.blackbox.test

import io.kotest.assertions.asClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import space.rybakov.timetable.api.v1.models.TripUpdateObject
import space.rybakov.timetable.blackbox.fixture.client.Client
import space.rybakov.timetable.blackbox.test.action.v1.createTrip
import space.rybakov.timetable.blackbox.test.action.v1.readTrip
import space.rybakov.timetable.blackbox.test.action.v1.updateTrip

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

        test("Update Trip ok") {
            val created = client.createTrip()
            client.updateTrip(created.id, created.lock, TripUpdateObject(name = "12а"))
            client.readTrip(created.id) {
            }
        }
    }
}