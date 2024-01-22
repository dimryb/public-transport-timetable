package space.rybakov.timetable.common.repo

import space.rybakov.timetable.common.helpers.errorRepoConcurrency
import space.rybakov.timetable.common.models.TimetableError
import space.rybakov.timetable.common.models.TimetableTrip
import space.rybakov.timetable.common.models.TimetableTripLock
import space.rybakov.timetable.common.helpers.errorEmptyId as timetableErrorEmptyId
import space.rybakov.timetable.common.helpers.errorNotFound as timetableErrorNotFound

data class DbTripResponse(
    override val data: TimetableTrip?,
    override val isSuccess: Boolean,
    override val errors: List<TimetableError> = emptyList()
): IDbResponse<TimetableTrip> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbTripResponse(null, true)
        fun success(result: TimetableTrip) = DbTripResponse(result, true)
        fun error(errors: List<TimetableError>, data: TimetableTrip? = null) = DbTripResponse(data, false, errors)
        fun error(error: TimetableError, data: TimetableTrip? = null) = DbTripResponse(data, false, listOf(error))

        val errorEmptyId = error(timetableErrorEmptyId)

        fun errorConcurrent(lock: TimetableTripLock, trip: TimetableTrip?) = error(
            errorRepoConcurrency(lock, trip?.lock?.let { TimetableTripLock(it.asString()) }),
            trip
        )

        val errorNotFound = error(timetableErrorNotFound)
    }
}
