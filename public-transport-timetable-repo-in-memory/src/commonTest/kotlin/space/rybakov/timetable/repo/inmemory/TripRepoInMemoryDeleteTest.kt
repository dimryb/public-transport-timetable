package space.rybakov.timetable.repo.inmemory

import space.rybakov.timetable.backend.repo.tests.RepoTripDeleteTest
import space.rybakov.timetable.common.repo.ITripRepository


class TripRepoInMemoryDeleteTest : RepoTripDeleteTest() {
    override val repo: ITripRepository = TripRepoInMemory(
        initObjects = initObjects
    )
}
