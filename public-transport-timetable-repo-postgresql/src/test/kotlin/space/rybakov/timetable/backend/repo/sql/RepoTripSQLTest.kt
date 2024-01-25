package space.rybakov.timetable.backend.repo.sql

import space.rybakov.timetable.backend.repo.tests.*
import space.rybakov.timetable.common.repo.ITripRepository

class RepoTripSQLCreateTest : RepoTripCreateTest() {
    override val repo: ITripRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoTripSQLDeleteTest : RepoTripDeleteTest() {
    override val repo: ITripRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoTripSQLReadTest : RepoTripReadTest() {
    override val repo: ITripRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoTripSQLSearchTest : RepoTripSearchTest() {
    override val repo: ITripRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoTripSQLUpdateTest : RepoTripUpdateTest() {
    override val repo: ITripRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
