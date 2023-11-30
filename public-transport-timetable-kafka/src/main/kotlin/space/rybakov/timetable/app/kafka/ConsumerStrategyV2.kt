package space.rybakov.timetable.app.kafka

import space.rybakov.timetable.api.v2.apiV2RequestDeserialize
import space.rybakov.timetable.api.v2.apiV2ResponseSerialize
import space.rybakov.timetable.api.v2.models.IRequest
import space.rybakov.timetable.api.v2.models.IResponse
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.mappers.v2.fromTransport
import space.rybakov.timetable.mappers.v2.toTransportTrip

class ConsumerStrategyV2 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV2, config.kafkaTopicOutV2)
    }

    override fun serialize(source: TimetableContext): String {
        val response: IResponse = source.toTransportTrip()
        return apiV2ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: TimetableContext) {
        val request: IRequest = apiV2RequestDeserialize(value)
        target.fromTransport(request)
    }
}