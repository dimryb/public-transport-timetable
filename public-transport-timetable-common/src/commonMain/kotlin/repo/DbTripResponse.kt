package space.rybakov.timetable.common.repo

import space.rybakov.timetable.common.models.TimetableError
import space.rybakov.timetable.common.models.TimetableTrip

data class DbTripResponse(
    override val data: TimetableTrip?,
    override val isSuccess: Boolean,
    override val errors: List<TimetableError> = emptyList()
): IDbResponse<TimetableTrip> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbTripResponse(null, true)
        fun success(result: TimetableTrip) = DbTripResponse(result, true)
        fun error(errors: List<TimetableError>) = DbTripResponse(null, false, errors)
        fun error(error: TimetableError) = DbTripResponse(null, false, listOf(error))
    }
}
