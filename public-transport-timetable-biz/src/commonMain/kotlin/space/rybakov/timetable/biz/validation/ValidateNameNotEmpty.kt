package space.rybakov.timetable.biz.validation

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.helpers.errorValidation
import space.rybakov.timetable.common.helpers.fail
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

// TODO-validation-4: смотрим пример COR DSL валидации
fun ICorChainDsl<TimetableContext>.validateNameNotEmpty(title: String) = worker {
    this.title = title
    on { tripValidating.name.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "name",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
