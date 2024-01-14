package space.rybakov.timetable.biz.validation

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.chain

fun ICorChainDsl<TimetableContext>.validation(block: ICorChainDsl<TimetableContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == TimetableState.RUNNING }
}
