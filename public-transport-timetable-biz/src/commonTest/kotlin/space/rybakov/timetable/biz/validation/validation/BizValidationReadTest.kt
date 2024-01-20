package space.rybakov.timetable.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import space.rybakov.timetable.backend.repository.inmemory.TripRepoStub
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.biz.validation.validation.validationIdCorrect
import space.rybakov.timetable.biz.validation.validation.validationIdEmpty
import space.rybakov.timetable.biz.validation.validation.validationIdFormat
import space.rybakov.timetable.biz.validation.validation.validationIdTrim
import space.rybakov.timetable.common.TimetableCorSettings
import space.rybakov.timetable.common.models.TimetableCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationReadTest {

    private val command = TimetableCommand.READ
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

