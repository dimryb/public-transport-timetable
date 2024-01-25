package space.rybakov.timetable.common.exceptions

import space.rybakov.timetable.common.models.TimetableTripLock

class RepoConcurrencyException(expectedLock: TimetableTripLock, actualLock: TimetableTripLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
