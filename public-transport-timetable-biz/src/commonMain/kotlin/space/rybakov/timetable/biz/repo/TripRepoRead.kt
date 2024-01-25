package space.rybakov.timetable.biz.repo

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.repo.DbTripIdRequest
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == TimetableState.RUNNING }
    handle {
        val request = DbTripIdRequest(tripValidated)
        val result = tripRepo.readTrip(request)
        val resultTrip = result.data
        if (result.isSuccess && resultTrip != null) {
            tripRepoRead = resultTrip
        } else {
            state = TimetableState.FAILING
            errors.addAll(result.errors)
        }
    }
}
