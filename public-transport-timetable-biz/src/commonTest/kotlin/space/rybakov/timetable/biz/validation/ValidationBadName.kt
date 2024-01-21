package space.rybakov.timetable.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.stubs.TimetableTripStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = TimetableTripStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameCorrect(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = stub.id,
            name = "abc",
            description = "abc",
            tripType = TimetableDirection.FORWARD,
            lock = TimetableTripLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(TimetableState.FAILING, ctx.state)
    assertEquals("abc", ctx.tripValidated.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameTrim(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = stub.id,
            name = " \n\t abc \t\n ",
            description = "abc",
            tripType = TimetableDirection.FORWARD,
            lock = TimetableTripLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(TimetableState.FAILING, ctx.state)
    assertEquals("abc", ctx.tripValidated.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameEmpty(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = stub.id,
            name = "",
            description = "abc",
            tripType = TimetableDirection.FORWARD,
            lock = TimetableTripLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TimetableState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("name", error?.field)
    assertContains(error?.message ?: "", "name")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameSymbols(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = TimetableTripId("123"),
            name = "!@#$%^&*(),.{}",
            description = "abc",
            tripType = TimetableDirection.FORWARD,
            lock = TimetableTripLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TimetableState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("name", error?.field)
    assertContains(error?.message ?: "", "name")
}
