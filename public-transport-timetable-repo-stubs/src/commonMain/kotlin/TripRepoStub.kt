package space.rybakov.timetable.backend.repository.inmemory

import space.rybakov.timetable.common.models.TimetableDirection
import space.rybakov.timetable.common.repo.*
import space.rybakov.timetable.stubs.TimetableTripStub

class TripRepoStub() : ITripRepository {
    override suspend fun createTrip(rq: DbTripRequest): DbTripResponse {
        return DbTripResponse(
            data = TimetableTripStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readTrip(rq: DbTripIdRequest): DbTripResponse {
        return DbTripResponse(
            data = TimetableTripStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateTrip(rq: DbTripRequest): DbTripResponse {
        return DbTripResponse(
            data = TimetableTripStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteTrip(rq: DbTripIdRequest): DbTripResponse {
        return DbTripResponse(
            data = TimetableTripStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchTrip(rq: DbTripFilterRequest): DbTripsResponse {
        return DbTripsResponse(
            data = TimetableTripStub.prepareSearchList(filter = "", TimetableDirection.FORWARD),
            isSuccess = true,
        )
    }
}
