package space.rybakov.timetable.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.*
import space.rybakov.timetable.common.stubs.TimetableStubs
import space.rybakov.timetable.stubs.TimetableTripStub
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TripCreateStubTest {

    private val processor = TimetableTripProcessor()
    val id = TimetableTripId("666")
    val name = "name 666"
    val description = "desc 666"
    val direction = TimetableDirection.FORWARD

    @Test
    fun create() = runTest {

        val ctx = TimetableContext(
            command = TimetableCommand.CREATE,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.SUCCESS,
            tripRequest = TimetableTrip(
                id = id,
                name = name,
                description = description,
                tripType = direction,
            ),
        )
        processor.exec(ctx)
        assertEquals(TimetableTripStub.get().id, ctx.tripResponse.id)
        assertEquals(name, ctx.tripResponse.name)
        assertEquals(description, ctx.tripResponse.description)
        assertEquals(direction, ctx.tripResponse.tripType)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = TimetableContext(
            command = TimetableCommand.CREATE,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.BAD_NAME,
            tripRequest = TimetableTrip(
                id = id,
                name = "",
                description = description,
                tripType = direction,
            ),
        )
        processor.exec(ctx)
        assertEquals(TimetableTrip(), ctx.tripResponse)
        assertEquals("name", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = TimetableContext(
            command = TimetableCommand.CREATE,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.BAD_DESCRIPTION,
            tripRequest = TimetableTrip(
                id = id,
                name = name,
                description = "",
                tripType = direction,
            ),
        )
        processor.exec(ctx)
        assertEquals(TimetableTrip(), ctx.tripResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = TimetableContext(
            command = TimetableCommand.CREATE,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.DB_ERROR,
            tripRequest = TimetableTrip(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(TimetableTrip(), ctx.tripResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = TimetableContext(
            command = TimetableCommand.CREATE,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.BAD_ID,
            tripRequest = TimetableTrip(
                id = id,
                name = name,
                description = description,
                tripType = direction,
            ),
        )
        processor.exec(ctx)
        assertEquals(TimetableTrip(), ctx.tripResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
