package space.rybakov.timetable.api.v2

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import space.rybakov.timetable.api.v2.models.*

/**
 * Добавляйте сюда элементы при появлении новых наследников IRequest / IResponse
 */
internal val infos = listOf(
    info(TripCreateRequest::class, IRequest::class, "create") { copy(requestType = it) },
    info(TripReadRequest::class, IRequest::class, "read") { copy(requestType = it) },
    info(TripUpdateRequest::class, IRequest::class, "update") { copy(requestType = it) },
    info(TripDeleteRequest::class, IRequest::class, "delete") { copy(requestType = it) },
    info(TripSearchRequest::class, IRequest::class, "search") { copy(requestType = it) },

    info(TripCreateResponse::class, IResponse::class, "create") { copy(responseType = it) },
    info(TripReadResponse::class, IResponse::class, "read") { copy(responseType = it) },
    info(TripUpdateResponse::class, IResponse::class, "update") { copy(responseType = it) },
    info(TripDeleteResponse::class, IResponse::class, "delete") { copy(responseType = it) },
    info(TripSearchResponse::class, IResponse::class, "search") { copy(responseType = it) },
    info(TripInitResponse::class, IResponse::class, "init") { copy(responseType = it) },
)

val apiV2Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        setupPolymorphic()
    }
}

fun apiV2RequestSerialize(request: IRequest): String = apiV2Mapper.encodeToString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> apiV2RequestDeserialize(json: String): T =
    apiV2Mapper.decodeFromString<IRequest>(json) as T

fun apiV2ResponseSerialize(response: IResponse): String = apiV2Mapper.encodeToString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IResponse> apiV2ResponseDeserialize(json: String): T =
    apiV2Mapper.decodeFromString<IResponse>(json) as T
