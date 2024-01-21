package space.rybakov.timetable.app.common

import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableCorSettings
import space.rybakov.timetable.logging.common.TtLoggerProvider

data class TimetableAppSettings(
    val appUrls: List<String> = emptyList(),
    val corSettings: TimetableCorSettings = TimetableCorSettings(),
    val processor: TimetableTripProcessor = TimetableTripProcessor(corSettings),
    val logger: TtLoggerProvider = TtLoggerProvider()
)