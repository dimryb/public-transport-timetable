package space.rybakov.timetable.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import space.rybakov.timetable.common.models.TimetableDirection
import space.rybakov.timetable.common.models.TimetableTrip
import space.rybakov.timetable.common.models.TimetableTripId
import space.rybakov.timetable.common.models.TimetableUserId
import space.rybakov.timetable.common.repo.DbTripRequest
import space.rybakov.timetable.common.repo.ITripRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoTripUpdateTest {
    abstract val repo: ITripRepository
    protected open val updateSucc = initObjects[0]
    private val updateIdNotFound = TimetableTripId("ad-repo-update-not-found")

    private val reqUpdateSucc by lazy {
        TimetableTrip(
            id = updateSucc.id,
            name = "update object",
            description = "update object description",
            ownerId = TimetableUserId("owner-123"),
            tripType = TimetableDirection.FORWARD,
        )
    }
    private val reqUpdateNotFound = TimetableTrip(
        id = updateIdNotFound,
        name = "update object not found",
        description = "update object not found description",
        ownerId = TimetableUserId("owner-123"),
        tripType = TimetableDirection.FORWARD,
    )

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateTrip(DbTripRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.name, result.data?.name)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.tripType, result.data?.tripType)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateTrip(DbTripRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitTrips("update") {
        override val initObjects: List<TimetableTrip> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
