package space.rybakov.timetable.blackbox.test

import io.kotest.core.spec.style.FunSpec
import space.rybakov.timetable.blackbox.fixture.client.Client
import space.rybakov.timetable.blackbox.test.action.v1.createTrip

fun FunSpec.testApiV1(client: Client) {
    context("v1") {
        test("Create Trip ok") {
            client.createTrip()
        }
    }
}