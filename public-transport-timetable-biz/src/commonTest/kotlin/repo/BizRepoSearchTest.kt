package repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import space.rybakov.timetable.backend.repo.tests.TripRepositoryMock
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.TimetableCorSettings
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.repo.DbTripsResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val userId = TimetableUserId("321")
    private val command = TimetableCommand.SEARCH
    private val initTrip = TimetableTrip(
        id = TimetableTripId("123"),
        name = "abc",
        description = "abc",
        ownerId = userId,
        tripType = TimetableDirection.FORWARD,
    )
    private val repo by lazy { TripRepositoryMock(
        invokeSearchTrip = {
            DbTripsResponse(
                isSuccess = true,
                data = listOf(initTrip),
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
    fun repoSearchSuccessTest() = runTest {
        val ctx = TimetableContext(
            command = command,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.TEST,
            tripFilterRequest = TimetableTripFilter(
                searchString = "ab",
                direction = TimetableDirection.FORWARD
            ),
        )
        processor.exec(ctx)
        assertEquals(TimetableState.FINISHING, ctx.state)
        assertEquals(1, ctx.tripsResponse.size)
    }
}
