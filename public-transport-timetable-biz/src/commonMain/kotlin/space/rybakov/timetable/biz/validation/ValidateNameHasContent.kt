package space.rybakov.timetable.biz.validation

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.helpers.errorValidation
import space.rybakov.timetable.common.helpers.fail
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.validateNameHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { tripValidating.name.isNotEmpty() && !tripValidating.name.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "name",
                violationCode = "noContent",
                description = "field must contain leters"
            )
        )
    }
}
