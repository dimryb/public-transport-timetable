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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val userId = TimetableUserId("321")
    private val command = TimetableCommand.DELETE
    private val initTrip = TimetableTrip(
        id = TimetableTripId("123"),
        name = "abc",
        description = "abc",
        ownerId = userId,
        tripType = TimetableDirection.FORWARD,
    )
    private val repo by lazy {
        TripRepositoryMock(
            invokeReadTrip = {
               DbTripResponse(
                   isSuccess = true,
                   data = initTrip,
               )
            },
            invokeDeleteTrip = {
                if (it.id == initTrip.id)
                    DbTripResponse(
                        isSuccess = true,
                        data = initTrip
                    )
                else DbTripResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy {
        TimetableCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { TimetableTripProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val adToUpdate = TimetableTrip(
            id = TimetableTripId("123"),
        )
        val ctx = TimetableContext(
            command = command,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.TEST,
            tripRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(TimetableState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initTrip.id, ctx.tripResponse.id)
        assertEquals(initTrip.name, ctx.tripResponse.name)
        assertEquals(initTrip.description, ctx.tripResponse.description)
        assertEquals(initTrip.tripType, ctx.tripResponse.tripType)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
