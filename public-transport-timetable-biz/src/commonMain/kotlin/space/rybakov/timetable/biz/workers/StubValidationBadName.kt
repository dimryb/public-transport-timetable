package space.rybakov.timetable.biz.workers

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableError
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.stubs.TimetableStubs
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.stubValidationBadName(title: String) = worker {
    this.title = title
    on { stubCase == TimetableStubs.BAD_NAME && state == TimetableState.RUNNING }
    handle {
        state = TimetableState.FAILING
        this.errors.add(
            TimetableError(
                group = "validation",
                code = "validation-name",
                field = "name",
                message = "Wrong name field"
            )
        )
    }
}
