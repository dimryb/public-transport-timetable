package space.rybakov.timetable.logging.jvm

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import space.rybakov.timetable.logging.common.ITtLogWrapper
import kotlin.reflect.KClass

/**
 * Generate internal TtLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun ttLoggerLogback(logger: Logger): ITtLogWrapper = TtLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun ttLoggerLogback(clazz: KClass<*>): ITtLogWrapper = ttLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun ttLoggerLogback(loggerId: String): ITtLogWrapper = ttLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
