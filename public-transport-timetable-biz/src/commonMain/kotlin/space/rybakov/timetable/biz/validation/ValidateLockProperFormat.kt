package space.rybakov.timetable.biz.validation

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.helpers.errorValidation
import space.rybakov.timetable.common.helpers.fail
import space.rybakov.timetable.common.models.TimetableTripLock
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в TimetableTripId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { tripValidating.lock != TimetableTripLock.NONE && !tripValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = tripValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
