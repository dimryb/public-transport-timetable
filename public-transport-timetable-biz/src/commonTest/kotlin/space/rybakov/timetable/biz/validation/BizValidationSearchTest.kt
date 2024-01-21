package space.rybakov.timetable.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import space.rybakov.timetable.backend.repository.inmemory.TripRepoStub
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.TimetableCorSettings
import space.rybakov.timetable.common.models.TimetableCommand
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.models.TimetableTripFilter
import space.rybakov.timetable.common.models.TimetableWorkMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = TimetableCommand.SEARCH
    private val settings by lazy {
        TimetableCorSettings(
            repoTest = TripRepoStub()
        )
    }
    private val processor by lazy { TimetableTripProcessor(settings) }

    @Test
    fun correctEmpty() = runTest {
        val ctx = TimetableContext(
            command = command,
            state = TimetableState.NONE,
            workMode = TimetableWorkMode.TEST,
            tripFilterRequest = TimetableTripFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(TimetableState.FAILING, ctx.state)
    }
}

