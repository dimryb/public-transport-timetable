package space.rybakov.timetable.repo.inmemory

import space.rybakov.timetable.backend.repo.tests.RepoTripUpdateTest
import space.rybakov.timetable.common.repo.ITripRepository

class TripRepoInMemoryUpdateTest : RepoTripUpdateTest() {
    override val repo: ITripRepository = TripRepoInMemory(
        initObjects = initObjects,
    )
}
