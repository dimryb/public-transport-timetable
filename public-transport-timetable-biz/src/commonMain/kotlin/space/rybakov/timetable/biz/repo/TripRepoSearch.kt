package space.rybakov.timetable.biz.repo

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.repo.DbTripFilterRequest
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == TimetableState.RUNNING }
    handle {
        val request = DbTripFilterRequest(
            nameFilter = tripFilterValidated.searchString,
            ownerId = tripFilterValidated.ownerId,
            direction = tripFilterValidated.direction,
        )
        val result = tripRepo.searchTrip(request)
        val resultTrips = result.data
        if (result.isSuccess && resultTrips != null) {
            tripsRepoDone = resultTrips.toMutableList()
        } else {
            state = TimetableState.FAILING
            errors.addAll(result.errors)
        }
    }
}
