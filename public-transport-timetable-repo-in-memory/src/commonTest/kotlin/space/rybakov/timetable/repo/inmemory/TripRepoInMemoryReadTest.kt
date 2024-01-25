package space.rybakov.timetable.repo.inmemory

import space.rybakov.timetable.backend.repo.tests.RepoTripReadTest
import space.rybakov.timetable.common.repo.ITripRepository

class TripRepoInMemoryReadTest: RepoTripReadTest() {
    override val repo: ITripRepository = TripRepoInMemory(
        initObjects = initObjects
    )
}
