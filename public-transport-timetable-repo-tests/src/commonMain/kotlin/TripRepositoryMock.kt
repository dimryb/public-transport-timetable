package space.rybakov.timetable.backend.repo.tests

import space.rybakov.timetable.common.repo.*

class TripRepositoryMock(
    private val invokeCreateTrip: (DbTripRequest) -> DbTripResponse = { DbTripResponse.MOCK_SUCCESS_EMPTY },
    private val invokeReadTrip: (DbTripIdRequest) -> DbTripResponse = { DbTripResponse.MOCK_SUCCESS_EMPTY },
    private val invokeUpdateTrip: (DbTripRequest) -> DbTripResponse = { DbTripResponse.MOCK_SUCCESS_EMPTY },
    private val invokeDeleteTrip: (DbTripIdRequest) -> DbTripResponse = { DbTripResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchTrip: (DbTripFilterRequest) -> DbTripsResponse = { DbTripsResponse.MOCK_SUCCESS_EMPTY },
) : ITripRepository {
    override suspend fun createTrip(rq: DbTripRequest): DbTripResponse {
        return invokeCreateTrip(rq)
    }

    override suspend fun readTrip(rq: DbTripIdRequest): DbTripResponse {
        return invokeReadTrip(rq)
    }

    override suspend fun updateTrip(rq: DbTripRequest): DbTripResponse {
        return invokeUpdateTrip(rq)
    }

    override suspend fun deleteTrip(rq: DbTripIdRequest): DbTripResponse {
        return invokeDeleteTrip(rq)
    }

    override suspend fun searchTrip(rq: DbTripFilterRequest): DbTripsResponse {
        return invokeSearchTrip(rq)
    }
}
