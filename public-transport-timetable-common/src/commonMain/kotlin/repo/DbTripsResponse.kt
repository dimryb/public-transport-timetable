package space.rybakov.timetable.common.repo

import space.rybakov.timetable.common.models.TimetableError
import space.rybakov.timetable.common.models.TimetableTrip

data class DbTripsResponse(
    override val data: List<TimetableTrip>?,
    override val isSuccess: Boolean,
    override val errors: List<TimetableError> = emptyList(),
): IDbResponse<List<TimetableTrip>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbTripsResponse(emptyList(), true)
        fun success(result: List<TimetableTrip>) = DbTripsResponse(result, true)
        fun error(errors: List<TimetableError>) = DbTripsResponse(null, false, errors)
        fun error(error: TimetableError) = DbTripsResponse(null, false, listOf(error))
    }
}
