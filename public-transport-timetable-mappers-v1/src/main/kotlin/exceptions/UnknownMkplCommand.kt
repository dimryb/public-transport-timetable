package space.rybakov.timetable.mappers.v1.exceptions

import space.rybakov.timetable.common.models.TimetableCommand

class UnknownTimetableCommand(command: TimetableCommand) : Throwable("Wrong command $command at mapping toTransport stage")
