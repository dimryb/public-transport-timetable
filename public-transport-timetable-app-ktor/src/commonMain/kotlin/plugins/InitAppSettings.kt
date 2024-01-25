package space.rybakov.timetable.app.plugins

import io.ktor.server.application.*
import space.rybakov.timetable.app.common.TimetableAppSettings
import space.rybakov.timetable.backend.repository.inmemory.TripRepoStub
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableCorSettings
import space.rybakov.timetable.logging.common.TtLoggerProvider
import space.rybakov.timetable.repo.inmemory.TripRepoInMemory

fun Application.initAppSettings(): TimetableAppSettings {
    val corSettings = TimetableCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoTest = TripRepoInMemory(),
        repoStub = TripRepoStub()
    )
    return TimetableAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        processor = TimetableTripProcessor(corSettings),
        logger = getLoggerProviderConf(),
    )
}
expect fun Application.getLoggerProviderConf(): TtLoggerProvider
