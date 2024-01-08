package space.rybakov.timetable.app.common

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.marketplace.api.logs.mapper.toLog
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.helpers.asTimetableError
import space.rybakov.timetable.common.helpers.fail
import space.rybakov.timetable.common.models.TimetableCommand
import space.rybakov.timetable.logging.common.ITtLogWrapper

suspend fun <T> TimetableTripProcessor.process(
    logger: ITtLogWrapper,
    logId: String,
    fromTransport: suspend TimetableContext.() -> Unit,
    sendResponse: suspend TimetableContext.() -> T): T {
    var command = TimetableCommand.NONE
    return try {
        val ctx = TimetableContext(
            timeStart = Clock.System.now(),
        )

        logger.doWithLogging(id = logId) {
            ctx.fromTransport()
            command = ctx.command

            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            ctx.sendResponse()
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            logger.error(msg = "$command handling failed")

            val ctx = TimetableContext(
                timeStart = Clock.System.now(),
                command = command
            )

            ctx.fail(e.asTimetableError())
            exec(ctx)
            sendResponse(ctx)
        }
    }
}
