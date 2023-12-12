package space.rybakov.timetable.blackbox.test

import space.rybakov.timetable.blackbox.docker.KafkaDockerCompose
import space.rybakov.timetable.blackbox.fixture.BaseFunSpec
import space.rybakov.timetable.blackbox.fixture.client.KafkaClient

class AccKafkaTest : BaseFunSpec(KafkaDockerCompose, {
    val client = KafkaClient(KafkaDockerCompose)

    testApiV1(client)
})
