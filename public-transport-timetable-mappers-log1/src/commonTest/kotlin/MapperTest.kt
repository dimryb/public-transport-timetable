
import ru.otus.otuskotlin.marketplace.api.logs.mapper.toLog
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {

    @Test
    fun fromContext() {
        val context = TimetableContext(
            requestId = TimetableRequestId("1234"),
            command = TimetableCommand.CREATE,
            tripResponse = TimetableTrip(
                name = "name",
                description = "desc",
                tripType = TimetableDirection.FORWARD,
            ),
            errors = mutableListOf(
                TimetableError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = TimetableState.RUNNING,
        )

        val log = context.toLog("test-id")

        assertEquals("test-id", log.logId)
        assertEquals("public-transport-timetable", log.source)
        assertEquals("1234", log.trip?.requestId)
        val error = log.errors?.firstOrNull()
        assertEquals("wrong title", error?.message)
        assertEquals("ERROR", error?.level)
    }
}
