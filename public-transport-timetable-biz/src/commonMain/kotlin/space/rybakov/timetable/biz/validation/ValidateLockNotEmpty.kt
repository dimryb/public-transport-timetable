package space.rybakov.timetable.biz.validation

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.helpers.errorValidation
import space.rybakov.timetable.common.helpers.fail
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title
    on { tripValidating.lock.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
