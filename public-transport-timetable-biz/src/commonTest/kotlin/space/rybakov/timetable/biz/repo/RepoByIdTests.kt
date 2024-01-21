package space.rybakov.timetable.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import space.rybakov.timetable.backend.repo.tests.TripRepositoryMock
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.TimetableCorSettings
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.repo.DbTripResponse
import kotlin.test.assertEquals

private val initTrip = TimetableTrip(
    id = TimetableTripId("123"),
    name = "abc",
    description = "abc",
    tripType = TimetableDirection.FORWARD,
)
private val repo = TripRepositoryMock(
        invokeReadTrip = {
            if (it.id == initTrip.id) {
                DbTripResponse(
                    isSuccess = true,
                    data = initTrip,
                )
            } else DbTripResponse(
                isSuccess = false,
                data = null,
                errors = listOf(TimetableError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    TimetableCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { TimetableTripProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: TimetableCommand) = runTest {
    val ctx = TimetableContext(
        command = command,
        state = TimetableState.NONE,
        workMode = TimetableWorkMode.TEST,
        tripRequest = TimetableTrip(
            id = TimetableTripId("12345"),
            name = "xyz",
            description = "xyz",
            tripType = TimetableDirection.FORWARD,
        ),
    )
    processor.exec(ctx)
    assertEquals(TimetableState.FAILING, ctx.state)
    assertEquals(TimetableTrip(), ctx.tripResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
