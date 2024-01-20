package space.rybakov.timetable.biz.validation.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import space.rybakov.timetable.backend.repository.inmemory.TripRepoStub
import space.rybakov.timetable.biz.TimetableTripProcessor
import space.rybakov.timetable.common.TimetableCorSettings
import space.rybakov.timetable.common.models.TimetableCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = TimetableCommand.UPDATE
    private val settings by lazy {
        TimetableCorSettings(
            repoTest = TripRepoStub()
        )
    }
    private val processor by lazy { TimetableTripProcessor(settings) }

    @Test fun correctTitle() = validationNameCorrect(command, processor)
    @Test fun trimTitle() = validationNameTrim(command, processor)
    @Test fun emptyTitle() = validationNameEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationNameSymbols(command, processor)

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

