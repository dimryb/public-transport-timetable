package space.rybakov.timetable.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import space.rybakov.timetable.backend.repository.inmemory.TripRepoStub
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableCorSettings
import space.rybakov.timetable.common.models.TimetableCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = TimetableCommand.DELETE
    private val settings by lazy {
        TimetableCorSettings(
            repoTest = TripRepoStub()
        )
    }
    private val processor by lazy { TimetableTripProcessor(settings) }

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

