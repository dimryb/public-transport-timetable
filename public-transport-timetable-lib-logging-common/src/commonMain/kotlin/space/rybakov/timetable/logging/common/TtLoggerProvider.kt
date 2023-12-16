package space.rybakov.timetable.logging.common

import kotlin.reflect.KClass

class TtLoggerProvider(
    private val provider: (String) -> ITtLogWrapper = { ITtLogWrapper.DEFAULT }
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")
}
