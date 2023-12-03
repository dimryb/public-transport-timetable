package space.rybakov.timetable.blackbox.test

import io.kotest.core.annotation.Ignored
import space.rybakov.timetable.blackbox.docker.WiremockDockerCompose
import space.rybakov.timetable.blackbox.fixture.BaseFunSpec
import space.rybakov.timetable.blackbox.fixture.client.RestClient
import space.rybakov.timetable.blackbox.fixture.docker.DockerCompose

@Ignored
open class AccRestTestBase(dockerCompose: DockerCompose) : BaseFunSpec(dockerCompose, {
    val client = RestClient(dockerCompose)

    testApiV1(client)
})
class AccRestWiremockTest : AccRestTestBase(WiremockDockerCompose)
// TODO class AccRestSpringTest : AccRestTestBase(SpringDockerCompose)
// TODO class AccRestKtorTest : AccRestTestBase(KtorDockerCompose)
