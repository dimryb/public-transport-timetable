package space.rybakov.timetable.biz.general

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.models.TimetableWorkMode
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.chain

fun ICorChainDsl<TimetableContext>.stubs(name: String, block: ICorChainDsl<TimetableContext>.() -> Unit) = chain {
    block()
    this.title = name
    on { workMode == TimetableWorkMode.STUB && state == TimetableState.RUNNING }
}
