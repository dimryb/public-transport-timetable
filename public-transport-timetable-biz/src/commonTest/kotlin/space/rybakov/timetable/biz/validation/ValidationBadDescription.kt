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
fun validationDescriptionCorrect(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
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
    assertEquals("abc", ctx.tripValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionTrim(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = stub.id,
            name = "abc",
            description = " \n\tabc \n\t",
            tripType = TimetableDirection.FORWARD,
            lock = TimetableTripLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(TimetableState.FAILING, ctx.state)
    assertEquals("abc", ctx.tripValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionEmpty(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = stub.id,
            name = "abc",
            description = "",
            tripType = TimetableDirection.FORWARD,
            lock = TimetableTripLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TimetableState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionSymbols(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = stub.id,
            name = "abc",
            description = "!@#$%^&*(),.{}",
            tripType = TimetableDirection.FORWARD,
            lock = TimetableTripLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TimetableState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}
