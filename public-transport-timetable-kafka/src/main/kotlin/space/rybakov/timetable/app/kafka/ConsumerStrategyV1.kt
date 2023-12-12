package space.rybakov.timetable.app.kafka

import space.rybakov.timetable.api.v1.models.IRequest
import space.rybakov.timetable.api.v1.models.IResponse
import space.rybakov.timetable.api.v1.models.apiV1RequestDeserialize
import space.rybakov.timetable.api.v1.models.apiV1ResponseSerialize
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.mappers.v1.fromTransport
import space.rybakov.timetable.mappers.v1.toTransportTrip

class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: TimetableContext): String {
        val response: IResponse = source.toTransportTrip()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: TimetableContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}