package space.rybakov.timetable.biz

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableCommand
import space.rybakov.timetable.common.models.TimetableDirection
import space.rybakov.timetable.common.models.TimetableWorkMode
import space.rybakov.timetable.stubs.TimetableTripStub

class TimetableTripProcessor {
    suspend fun exec(ctx: TimetableContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == TimetableWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            TimetableCommand.SEARCH -> {
                ctx.tripsResponse.addAll(TimetableTripStub.prepareSearchList("11", TimetableDirection.FORWARD))
            }
            else -> {
                ctx.tripResponse = TimetableTripStub.get()
            }
        }
    }
}
