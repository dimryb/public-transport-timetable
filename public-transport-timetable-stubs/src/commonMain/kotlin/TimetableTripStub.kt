package space.rybakov.timetable.stubs

import space.rybakov.timetable.common.models.TimetableTrip
import space.rybakov.timetable.common.models.TimetableTripId
import space.rybakov.timetable.common.models.TimetableDirection
import space.rybakov.timetable.stubs.TimetableTripStubBolts.TRIP_DEMAND_11
import space.rybakov.timetable.stubs.TimetableTripStubBolts.TRIP_SUPPLY_11

object TimetableTripStub {
    fun get(): TimetableTrip = TRIP_DEMAND_11.copy()

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
        timetableTrip(TRIP_DEMAND_11, id = id, filter = filter, type = type)

    private fun timetableTripSupply(id: String, filter: String, type: TimetableDirection) =
        timetableTrip(TRIP_SUPPLY_11, id = id, filter = filter, type = type)

    private fun timetableTrip(base: TimetableTrip, id: String, filter: String, type: TimetableDirection) = base.copy(
        id = TimetableTripId(id),
        name = "$filter $id",
        description = "desc $filter $id",
        tripType = type,
    )

}
