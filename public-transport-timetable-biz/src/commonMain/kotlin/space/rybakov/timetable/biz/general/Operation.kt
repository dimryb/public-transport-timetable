package space.rybakov.timetable.biz.general

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableCommand
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.chain

fun ICorChainDsl<TimetableContext>.operation(name: String, command: TimetableCommand, block: ICorChainDsl<TimetableContext>.() -> Unit) = chain {
    block()
    this.title = name
    on { this.command == command && state == TimetableState.RUNNING }
}
