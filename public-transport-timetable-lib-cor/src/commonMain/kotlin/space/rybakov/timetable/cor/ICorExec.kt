package space.rybakov.timetable.cor

/**
 * Блок кода, который обрабатывает контекст. Имеет имя и описание
 */
interface ICorExec<T> {
    val title: String
    val description: String
    suspend fun exec(context: T)
}
