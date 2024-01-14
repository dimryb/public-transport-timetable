package space.rybakov.timetable.biz.validation

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == TimetableState.RUNNING }
    handle {
        tripValidated = tripValidating
    }
}

fun ICorChainDsl<TimetableContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == TimetableState.RUNNING }
    handle {
        tripFilterValidated = tripFilterValidating
    }
}
