package space.rybakov.timetable.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import space.rybakov.timetable.backend.repo.tests.TripRepositoryMock
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.TimetableCorSettings
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.repo.DbTripResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = TimetableUserId("321")
    private val command = TimetableCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = TripRepositoryMock(
        invokeCreateTrip = {
            DbTripResponse(
                isSuccess = true,
                data = TimetableTrip(
                    id = TimetableTripId(uuid),
                    name = it.trip.name,
                    description = it.trip.description,
                    ownerId = userId,
                    tripType = it.trip.tripType,
                )
            )
        }
    )
    private val settings = TimetableCorSettings(
        repoTest = repo
    )
    private val processor = TimetableTripProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = TimetableContext(
            command = command,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.TEST,
            tripRequest = TimetableTrip(
                name = "abc",
                description = "abc",
                tripType = TimetableDirection.FORWARD,
            ),
        )
        processor.exec(ctx)
        assertEquals(TimetableState.FINISHING, ctx.state)
        assertNotEquals(TimetableTripId.NONE, ctx.tripResponse.id)
        assertEquals("abc", ctx.tripResponse.name)
        assertEquals("abc", ctx.tripResponse.description)
        assertEquals(TimetableDirection.FORWARD, ctx.tripResponse.tripType)
    }
}
