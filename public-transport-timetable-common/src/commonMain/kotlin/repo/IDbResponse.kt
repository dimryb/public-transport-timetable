package space.rybakov.timetable.common.repo

import space.rybakov.timetable.common.models.TimetableError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<TimetableError>
}
