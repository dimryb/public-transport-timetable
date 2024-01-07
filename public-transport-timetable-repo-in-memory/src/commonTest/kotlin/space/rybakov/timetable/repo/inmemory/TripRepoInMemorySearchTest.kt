package space.rybakov.timetable.repo.inmemory

import space.rybakov.timetable.backend.repo.tests.RepoTripSearchTest
import space.rybakov.timetable.common.repo.ITripRepository

class TripRepoInMemorySearchTest : RepoTripSearchTest() {
    override val repo: ITripRepository = TripRepoInMemory(
        initObjects = initObjects
    )
}
