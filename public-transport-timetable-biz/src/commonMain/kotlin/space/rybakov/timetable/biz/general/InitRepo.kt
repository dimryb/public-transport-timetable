package space.rybakov.timetable.biz.general

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.helpers.errorAdministration
import space.rybakov.timetable.common.helpers.fail
import space.rybakov.timetable.common.models.TimetableWorkMode
import space.rybakov.timetable.common.repo.ITripRepository
import space.rybakov.timetable.cor.ICorChainDsl
import space.rybakov.timetable.cor.worker

fun ICorChainDsl<TimetableContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        tripRepo = when {
            workMode == TimetableWorkMode.TEST -> settings.repoTest
            workMode == TimetableWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != TimetableWorkMode.STUB && tripRepo == ITripRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
