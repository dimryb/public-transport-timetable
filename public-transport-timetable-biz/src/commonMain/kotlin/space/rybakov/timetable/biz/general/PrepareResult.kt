package space.rybakov.timetable.biz.general

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.models.TimetableWorkMode
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != TimetableWorkMode.STUB }
    handle {
        tripResponse = tripRepoDone
        tripsResponse = tripsRepoDone
        state = when (val st = state) {
            TimetableState.RUNNING -> TimetableState.FINISHING
            else -> st
        }
    }
}
