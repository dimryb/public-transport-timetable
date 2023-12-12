package space.rybakov.timetable.blackbox.test.action.v1

import mu.KotlinLogging
import space.rybakov.timetable.api.v1.models.IRequest
import space.rybakov.timetable.api.v1.models.IResponse
import space.rybakov.timetable.api.v1.models.apiV1RequestSerialize
import space.rybakov.timetable.api.v1.models.apiV1ResponseDeserialize
import space.rybakov.timetable.blackbox.fixture.client.Client

private val log = KotlinLogging.logger {}

suspend fun Client.sendAndReceive(path: String, request: IRequest): IResponse {
    val requestBody = apiV1RequestSerialize(request)
    log.info { "Send to v1/$path\n$requestBody" }

    val responseBody = sendAndReceive("v1", path, requestBody)
    log.info { "Received\n$responseBody" }

    return apiV1ResponseDeserialize(responseBody)
}