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
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class TripSearchStubTest {

    private val processor = TimetableTripProcessor()
    val filter = TimetableTripFilter(searchString = "12а")

    @Test
    fun read() = runTest {

        val ctx = TimetableContext(
            command = TimetableCommand.SEARCH,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.SUCCESS,
            tripFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.tripsResponse.size > 1)
        val first = ctx.tripsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.name.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (TimetableTripStub.get()) {
            assertEquals(tripType, first.tripType)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = TimetableContext(
            command = TimetableCommand.SEARCH,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.BAD_ID,
            tripFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(TimetableTrip(), ctx.tripResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = TimetableContext(
            command = TimetableCommand.SEARCH,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.DB_ERROR,
            tripFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(TimetableTrip(), ctx.tripResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = TimetableContext(
            command = TimetableCommand.SEARCH,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.STUB,
            stubCase = TimetableStubs.BAD_NAME,
            tripFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(TimetableTrip(), ctx.tripResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
