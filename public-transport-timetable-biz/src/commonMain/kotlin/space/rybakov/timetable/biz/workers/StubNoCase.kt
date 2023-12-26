package space.rybakov.timetable.biz.workers

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.helpers.fail
import space.rybakov.timetable.common.models.TimetableError
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == TimetableState.RUNNING }
    handle {
        fail(
            TimetableError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
