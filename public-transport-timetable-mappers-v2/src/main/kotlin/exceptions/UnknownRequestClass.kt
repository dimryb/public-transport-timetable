package space.rybakov.timetable.mappers.v2.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to TimetableContext")
