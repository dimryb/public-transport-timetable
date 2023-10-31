rootProject.name = "public-transport-timetable"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val ktorVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false

        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false

        id("io.ktor.plugin") version ktorVersion apply false
    }
}

//include("m1l1-hello")
include("public-transport-timetable-acceptance")
include("public-transport-timetable-api-v1-jackson")
include("public-transport-timetable-common")
include("public-transport-timetable-mappers-v1")
include("public-transport-timetable-app-ktor")
