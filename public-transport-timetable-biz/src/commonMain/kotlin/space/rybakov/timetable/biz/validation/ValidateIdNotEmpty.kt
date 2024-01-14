package space.rybakov.timetable.biz.validation

import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker
import space.rybakov.timetable.common.helpers.errorValidation
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.helpers.fail

fun ICorChainDsl<TimetableContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { tripValidating.id.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
