package space.rybakov.timetable.biz.workers

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableError
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.stubs.TimetableStubs
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.stubValidationBadDescription(title: String) = worker {
    this.title = title
    on { stubCase == TimetableStubs.BAD_DESCRIPTION && state == TimetableState.RUNNING }
    handle {
        state = TimetableState.FAILING
        this.errors.add(
            TimetableError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description field"
            )
        )
    }
}
