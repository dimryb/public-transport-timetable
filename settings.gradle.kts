rootProject.name = "public-transport-timetable"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val ktorVersion: String by settings
    val bmuschkoVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false

        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false

        id("io.ktor.plugin") version ktorVersion apply false

        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-spring-boot-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false
    }
}

//include("m1l1-hello")
include("public-transport-timetable-acceptance")
include("public-transport-timetable-api-v1-jackson")
include("public-transport-timetable-api-v2-kmp")
include("public-transport-timetable-common")
include("public-transport-timetable-mappers-v1")
include("public-transport-timetable-mappers-v2")
include("public-transport-timetable-api-log1")
include("public-transport-timetable-app-ktor")
include("public-transport-timetable-biz")
include("public-transport-timetable-stubs")
include("public-transport-timetable-kafka")

include("public-transport-timetable-lib-logging-common")
include("public-transport-timetable-lib-logging-kermit")
include("public-transport-timetable-lib-logging-logback")