package repo

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

class BizRepoReadTest {

    private val userId = TimetableUserId("321")
    private val command = TimetableCommand.READ
    private val initTrip = TimetableTrip(
        id = TimetableTripId("123"),
        name = "abc",
        description = "abc",
        ownerId = userId,
        tripType = TimetableDirection.FORWARD,
    )
    private val repo by lazy { TripRepositoryMock(
        invokeReadTrip = {
            DbTripResponse(
                isSuccess = true,
                data = initTrip,
            )
        }
    ) }
    private val settings by lazy {
        TimetableCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { TimetableTripProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = TimetableContext(
            command = command,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.TEST,
            tripRequest = TimetableTrip(
                id = TimetableTripId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(TimetableState.FINISHING, ctx.state)
        assertEquals(initTrip.id, ctx.tripResponse.id)
        assertEquals(initTrip.name, ctx.tripResponse.name)
        assertEquals(initTrip.description, ctx.tripResponse.description)
        assertEquals(initTrip.tripType, ctx.tripResponse.tripType)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
