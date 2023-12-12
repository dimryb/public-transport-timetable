package space.rybakov.timetable.blackbox.test.action.v1

import space.rybakov.timetable.api.v1.models.*

val debug = TripDebug(mode = TripRequestDebugMode.STUB, stub = TripRequestDebugStubs.SUCCESS)

val tripStub = TripResponseObject(
    name = "11",
    description = "Муниципальный маршрут 11",
    tripType = Direction.FORWARD,
    id = "666",
    ownerId = "user-1",
    permissions = setOf(
        TripPermissions.DELETE,
        TripPermissions.READ,
        TripPermissions.UPDATE,
    )
)

val someCreateTrip = TripCreateObject(
    name = "11",
    description = "Муниципальный маршрут 11",
    tripType = Direction.FORWARD,
)
