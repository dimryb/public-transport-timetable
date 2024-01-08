package space.rybakov.timetable.app.plugins

import io.ktor.server.application.*
import space.rybakov.timetable.logging.common.TtLoggerProvider
import space.rybakov.timetable.logging.jvm.ttLoggerLogback
import space.rybakov.timetable.logging.kermit.ttLoggerKermit

actual fun Application.getLoggerProviderConf(): TtLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "kmp" -> TtLoggerProvider { ttLoggerKermit(it) }
        "logback", null -> TtLoggerProvider { ttLoggerLogback(it) }
        else -> throw Exception("Logger $mode is not allowed. Admitted values are kmp and logback")
}
