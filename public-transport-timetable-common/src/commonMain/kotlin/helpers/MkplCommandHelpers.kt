package space.rybakov.timetable.common.helpers

import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.models.TimetableCommand

fun TimetableContext.isUpdatableCommand() =
    this.command in listOf(TimetableCommand.CREATE, TimetableCommand.UPDATE, TimetableCommand.DELETE)
