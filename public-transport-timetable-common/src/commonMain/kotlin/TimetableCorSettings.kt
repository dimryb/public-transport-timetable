package space.rybakov.timetable.common

import space.rybakov.timetable.common.repo.ITripRepository
import space.rybakov.timetable.logging.common.TtLoggerProvider

data class TimetableCorSettings(
    val loggerProvider: TtLoggerProvider = TtLoggerProvider(),
    val repoStub: ITripRepository = ITripRepository.NONE,
    val repoTest: ITripRepository = ITripRepository.NONE,
    val repoProd: ITripRepository = ITripRepository.NONE,
) {
    companion object {
        val NONE = TimetableCorSettings()
    }
}
