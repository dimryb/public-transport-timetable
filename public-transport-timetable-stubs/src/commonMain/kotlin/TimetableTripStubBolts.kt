package space.rybakov.timetable.stubs

import space.rybakov.timetable.common.models.*

object TimetableTripStubBolts {
    val TRIP_DEMAND_11: TimetableTrip
        get() = TimetableTrip(
            id = TimetableTripId("666"),
            name = "11",
            description = "Муниципальный маршрут 11",
            ownerId = TimetableUserId("user-1"),
            tripType = TimetableDirection.FORWARD,
            permissionsClient = mutableSetOf(
                TimetableTripPermissionClient.READ,
                TimetableTripPermissionClient.UPDATE,
                TimetableTripPermissionClient.DELETE,
            )
        )
    val TRIP_SUPPLY_11 = TRIP_DEMAND_11.copy(tripType = TimetableDirection.FORWARD)
}
