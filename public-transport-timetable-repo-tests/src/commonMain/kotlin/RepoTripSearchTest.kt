package space.rybakov.timetable.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import space.rybakov.timetable.common.models.TimetableDirection
import space.rybakov.timetable.common.models.TimetableTrip
import space.rybakov.timetable.common.models.TimetableUserId
import space.rybakov.timetable.common.repo.DbTripFilterRequest
import space.rybakov.timetable.common.repo.ITripRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoTripSearchTest {
    abstract val repo: ITripRepository

    protected open val initializedObjects: List<TimetableTrip> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchTrip(DbTripFilterRequest(ownerId = searchOwnerId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun searchDirection() = runRepoTest {
        val result = repo.searchTrip(DbTripFilterRequest(direction = TimetableDirection.FORWARD))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitTrips("search") {

        val searchOwnerId = TimetableUserId("owner-124")
        override val initObjects: List<TimetableTrip> = listOf(
            createInitTestModel("trip1"),
            createInitTestModel("trip2", ownerId = searchOwnerId),
            createInitTestModel("trip3", tripType = TimetableDirection.FORWARD),
            createInitTestModel("trip4", ownerId = searchOwnerId),
            createInitTestModel("trip5", tripType = TimetableDirection.FORWARD),
        )
    }
}
