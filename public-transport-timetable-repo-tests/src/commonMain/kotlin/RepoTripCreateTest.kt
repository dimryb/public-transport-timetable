package space.rybakov.timetable.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.repo.DbTripRequest
import space.rybakov.timetable.common.repo.ITripRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoTripCreateTest {
    abstract val repo: ITripRepository

    protected open val lockNew: TimetableTripLock = TimetableTripLock("20000000-0000-0000-0000-000000000002")

    private val createObj = TimetableTrip(
        name = "create object",
        description = "create object description",
        ownerId = TimetableUserId("owner-123"),
        tripType = TimetableDirection.FORWARD,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createTrip(DbTripRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: TimetableTripId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.name, result.data?.name)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.tripType, result.data?.tripType)
        assertNotEquals(TimetableTripId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitTrips("create") {
        override val initObjects: List<TimetableTrip> = emptyList()
    }
}
