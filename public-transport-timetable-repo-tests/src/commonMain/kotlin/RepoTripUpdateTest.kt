package space.rybakov.timetable.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.repo.DbTripRequest
import space.rybakov.timetable.common.repo.ITripRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoTripUpdateTest {
    abstract val repo: ITripRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    private val updateIdNotFound = TimetableTripId("ad-repo-update-not-found")
    protected val lockBad = TimetableTripLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = TimetableTripLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        TimetableTrip(
            id = updateSucc.id,
            name = "update object",
            description = "update object description",
            ownerId = TimetableUserId("owner-123"),
            tripType = TimetableDirection.FORWARD,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = TimetableTrip(
        id = updateIdNotFound,
        name = "update object not found",
        description = "update object not found description",
        ownerId = TimetableUserId("owner-123"),
        tripType = TimetableDirection.FORWARD,
        lock = initObjects.first().lock,
    )

    private val reqUpdateConc by lazy {
        TimetableTrip(
            id = updateConc.id,
            name = "update object not found",
            description = "update object not found description",
            ownerId = TimetableUserId("owner-123"),
            tripType = TimetableDirection.FORWARD,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateTrip(DbTripRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.name, result.data?.name)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.tripType, result.data?.tripType)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateTrip(DbTripRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateTrip(DbTripRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitTrips("update") {
        override val initObjects: List<TimetableTrip> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
