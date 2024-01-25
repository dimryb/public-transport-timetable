package space.rybakov.timetable.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class TimetableTripLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = TimetableTripLock("")
    }
}
