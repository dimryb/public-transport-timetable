package space.rybakov.timetable.common.helpers

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableError

fun Throwable.asTimetableError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = TimetableError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun TimetableContext.addError(vararg error: TimetableError) = errors.addAll(error)
