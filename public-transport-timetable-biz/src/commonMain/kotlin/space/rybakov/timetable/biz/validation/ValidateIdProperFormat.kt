package space.rybakov.timetable.biz.validation

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.helpers.errorValidation
import space.rybakov.timetable.common.helpers.fail
import space.rybakov.timetable.common.models.TimetableTripId
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в TimetableTripId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { tripValidating.id != TimetableTripId.NONE && !tripValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = tripValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}
