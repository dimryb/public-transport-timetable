package space.rybakov.timetable.stubs

import space.rybakov.timetable.common.models.*

object TimetableTripStub {
    fun get() = TimetableTrip(
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

    fun prepareResult(block: TimetableTrip.() -> Unit): TimetableTrip = get().apply(block)

    fun prepareSearchList(filter: String, type: TimetableDirection) = listOf(
        timetableTripDemand("d-666-01", filter, type),
        timetableTripDemand("d-666-02", filter, type),
        timetableTripDemand("d-666-03", filter, type),
        timetableTripDemand("d-666-04", filter, type),
        timetableTripDemand("d-666-05", filter, type),
        timetableTripDemand("d-666-06", filter, type),
    )

    private fun timetableTripDemand(id: String, filter: String, type: TimetableDirection) =
        timetableTrip(get(), id = id, filter = filter, type = type)

    private fun timetableTripSupply(id: String, filter: String, type: TimetableDirection) =
        timetableTrip(get(), id = id, filter = filter, type = type)

    private fun timetableTrip(base: TimetableTrip, id: String, filter: String, type: TimetableDirection) = base.copy(
        id = TimetableTripId(id),
        name = "$filter $id",
        description = "desc $filter $id",
        tripType = type,
    )

}
