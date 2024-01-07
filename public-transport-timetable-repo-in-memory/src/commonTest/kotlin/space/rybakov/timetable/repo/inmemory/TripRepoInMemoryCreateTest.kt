package space.rybakov.timetable.repo.inmemory

import space.rybakov.timetable.backend.repo.tests.RepoTripCreateTest

class TripRepoInMemoryCreateTest : RepoTripCreateTest() {
    override val repo = TripRepoInMemory(
        initObjects = initObjects,
    )
}