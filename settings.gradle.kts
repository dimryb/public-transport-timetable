rootProject.name = "public-transport-timetable"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false

        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}

//include("m1l1-hello")
include("public-transport-timetable-acceptance")
include("public-transport-timetable-api-v1-jackson")
