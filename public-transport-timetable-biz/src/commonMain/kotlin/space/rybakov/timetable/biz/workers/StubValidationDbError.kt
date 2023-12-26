package space.rybakov.timetable.biz.workers

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableError
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.stubs.TimetableStubs
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == TimetableStubs.DB_ERROR && state == TimetableState.RUNNING }
    handle {
        state = TimetableState.FAILING
        this.errors.add(
            TimetableError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
