package space.rybakov.timetable.logging.kermit

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import space.rybakov.timetable.logging.common.ITtLogWrapper
import kotlin.reflect.KClass

@Suppress("unused")
fun ttLoggerKermit(loggerId: String): ITtLogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return TtLoggerWrapperKermit(
        logger = logger,
        loggerId = loggerId,
    )
}

@Suppress("unused")
fun ttLoggerKermit(cls: KClass<*>): ITtLogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return TtLoggerWrapperKermit(
        logger = logger,
        loggerId = cls.qualifiedName?: "",
    )
}
