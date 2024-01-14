package space.rybakov.timetable.common.helpers

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableError
import space.rybakov.timetable.common.models.TimetableState

fun Throwable.asTimetableError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = TimetableError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun TimetableContext.addError(vararg error: TimetableError) = errors.addAll(error)

fun TimetableContext.fail(error: TimetableError) {
    addError(error)
    state = TimetableState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: TimetableError.Level = TimetableError.Level.ERROR,
) = TimetableError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    level: TimetableError.Level = TimetableError.Level.ERROR,
) = TimetableError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
)