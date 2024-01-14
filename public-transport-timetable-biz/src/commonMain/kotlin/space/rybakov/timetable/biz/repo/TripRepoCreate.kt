package space.rybakov.timetable.biz.repo

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.repo.DbTripRequest
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление объявления в БД"
    on { state == TimetableState.RUNNING }
    handle {
        val request = DbTripRequest(tripRepoPrepare)
        val result = tripRepo.createTrip(request)
        val resultTrip = result.data
        if (result.isSuccess && resultTrip != null) {
            tripRepoDone = resultTrip
        } else {
            state = TimetableState.FAILING
            errors.addAll(result.errors)
        }
    }
}
