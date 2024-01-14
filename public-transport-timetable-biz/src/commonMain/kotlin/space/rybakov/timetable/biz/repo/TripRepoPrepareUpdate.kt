package space.rybakov.timetable.biz.repo

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == TimetableState.RUNNING }
    handle {
        tripRepoPrepare = tripRepoRead.deepCopy().apply {
            this.name = tripValidated.name
            description = tripValidated.description
            tripType = tripValidated.tripType
        }
    }
}
