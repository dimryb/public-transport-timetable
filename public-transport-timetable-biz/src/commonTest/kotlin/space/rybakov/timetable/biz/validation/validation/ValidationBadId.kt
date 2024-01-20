package space.rybakov.timetable.biz.validation.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = TimetableTripId("123-234-abc-ABC"),
            name = "abc",
            description = "abc",
            tripType = TimetableDirection.FORWARD,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(TimetableState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = TimetableTripId(" \n\t 123-234-abc-ABC \n\t "),
            name = "abc",
            description = "abc",
            tripType = TimetableDirection.FORWARD,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(TimetableState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = TimetableTripId(""),
            name = "abc",
            description = "abc",
            tripType = TimetableDirection.FORWARD,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TimetableState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: TimetableCommand, processor: TimetableTripProcessor) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = TimetableTripId("!@#\$%^&*(),.{}"),
            name = "abc",
            description = "abc",
            tripType = TimetableDirection.FORWARD,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TimetableState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
