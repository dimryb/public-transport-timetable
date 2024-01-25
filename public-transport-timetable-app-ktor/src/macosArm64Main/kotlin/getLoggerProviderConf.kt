package space.rybakov.timetable.app.plugins

import io.ktor.server.application.*
import space.rybakov.timetable.logging.common.TtLoggerProvider
import space.rybakov.timetable.logging.kermit.ttLoggerKermit

actual fun Application.getLoggerProviderConf(): TtLoggerProvider = TtLoggerProvider {
    ttLoggerKermit(it)
}
