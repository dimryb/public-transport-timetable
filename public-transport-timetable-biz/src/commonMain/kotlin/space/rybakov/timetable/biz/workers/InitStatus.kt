package space.rybakov.timetable.biz.workers

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == TimetableState.NONE }
    handle { state = TimetableState.RUNNING }
}
