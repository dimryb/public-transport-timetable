package space.rybakov.timetable.biz.repo

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.repo.DbTripIdRequest
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == TimetableState.RUNNING }
    handle {
        val request = DbTripIdRequest(tripRepoPrepare)
        val result = tripRepo.deleteTrip(request)
        if (!result.isSuccess) {
            state = TimetableState.FAILING
            errors.addAll(result.errors)
        }
        tripRepoDone = tripRepoRead
    }
}
