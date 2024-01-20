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

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val userId = TimetableUserId("321")
    private val command = TimetableCommand.UPDATE
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
        },
        invokeUpdateTrip = {
            DbTripResponse(
                isSuccess = true,
                data = TimetableTrip(
                    id = TimetableTripId("123"),
                    name = "xyz",
                    description = "xyz",
                    tripType = TimetableDirection.FORWARD,
                )
            )
        }
    ) }
    private val settings by lazy {
        TimetableCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { TimetableTripProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val adToUpdate = TimetableTrip(
            id = TimetableTripId("123"),
            name = "xyz",
            description = "xyz",
            tripType = TimetableDirection.FORWARD,
        )
        val ctx = TimetableContext(
            command = command,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.TEST,
            tripRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(TimetableState.FINISHING, ctx.state)
        assertEquals(adToUpdate.id, ctx.tripResponse.id)
        assertEquals(adToUpdate.name, ctx.tripResponse.name)
        assertEquals(adToUpdate.description, ctx.tripResponse.description)
        assertEquals(adToUpdate.tripType, ctx.tripResponse.tripType)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
