package space.rybakov.timetable.biz.workers

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.stubs.TimetableStubs
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker
import space.rybakov.timetable.stubs.TimetableTripStub

fun ICorChainDsl<TimetableContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == TimetableStubs.SUCCESS && state == TimetableState.RUNNING }
    handle {
        state = TimetableState.FINISHING
        val stub = TimetableTripStub.prepareResult {
            tripRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
        }
        tripResponse = stub
    }
}
