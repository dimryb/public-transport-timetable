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
class TripDeleteStubTest {

    private val processor = TimetableTripProcessor()
    val id = TimetableTripId("666")

    @Test
    fun delete() = runTest {

        val ctx = TimetableContext(
            command = TimetableCommand.DELETE,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.SUCCESS,
            tripRequest = TimetableTrip(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = TimetableTripStub.get()
        assertEquals(stub.id, ctx.tripResponse.id)
        assertEquals(stub.name, ctx.tripResponse.name)
        assertEquals(stub.description, ctx.tripResponse.description)
        assertEquals(stub.tripType, ctx.tripResponse.tripType)
    }

    @Test
    fun badId() = runTest {
        val ctx = TimetableContext(
            command = TimetableCommand.DELETE,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.BAD_ID,
            tripRequest = TimetableTrip(),
        )
        processor.exec(ctx)
        assertEquals(TimetableTrip(), ctx.tripResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = TimetableContext(
            command = TimetableCommand.DELETE,
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
            command = TimetableCommand.DELETE,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.BAD_NAME,
            tripRequest = TimetableTrip(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(TimetableTrip(), ctx.tripResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
