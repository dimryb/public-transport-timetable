package space.rybakov.timetable.common.repo

interface ITripRepository {
    suspend fun createTrip(rq: DbTripRequest): DbTripResponse
    suspend fun readTrip(rq: DbTripIdRequest): DbTripResponse
    suspend fun updateTrip(rq: DbTripRequest): DbTripResponse
    suspend fun deleteTrip(rq: DbTripIdRequest): DbTripResponse
    suspend fun searchTrip(rq: DbTripFilterRequest): DbTripsResponse
    companion object {
        val NONE = object : ITripRepository {
            override suspend fun createTrip(rq: DbTripRequest): DbTripResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readTrip(rq: DbTripIdRequest): DbTripResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateTrip(rq: DbTripRequest): DbTripResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteTrip(rq: DbTripIdRequest): DbTripResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchTrip(rq: DbTripFilterRequest): DbTripsResponse {
                TODO("Not yet implemented")
            }
        }
    }
}
