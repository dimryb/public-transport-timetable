package space.rybakov.timetable.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import space.rybakov.timetable.common.models.TimetableTrip
import space.rybakov.timetable.common.models.TimetableTripId
import space.rybakov.timetable.common.repo.DbTripIdRequest
import space.rybakov.timetable.common.repo.ITripRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoTripDeleteTest {
    abstract val repo: ITripRepository
    protected open val deleteSucc = initObjects[0]

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteTrip(DbTripIdRequest(deleteSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readTrip(DbTripIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitTrips("delete") {
        override val initObjects: List<TimetableTrip> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
        val notFoundId = TimetableTripId("trip-repo-delete-notFound")
    }
}
