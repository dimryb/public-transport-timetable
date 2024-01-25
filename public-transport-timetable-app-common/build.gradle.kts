plugins {
//    kotlin("plugin.serialization")
    kotlin("multiplatform")
}
kotlin {
    jvm {}
    //linuxX64 {}
    macosX64 {}
    macosArm64 {}

    sourceSets {
        val logbackVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api(project(":public-transport-timetable-common"))
                api(project(":public-transport-timetable-biz"))

                // v2 api
                api(project(":public-transport-timetable-api-v2-kmp"))
                api(project(":public-transport-timetable-mappers-v2"))

                // biz
                api(project(":public-transport-timetable-biz"))

                // logging
                api(project(":public-transport-timetable-lib-logging-common"))
                api(project(":public-transport-timetable-lib-logging-kermit"))
                api(project(":public-transport-timetable-mappers-log1"))
                api(project(":public-transport-timetable-api-log1"))

                // Stubs
//                implementation(project(":public-transport-timetable-stubs"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                api("ch.qos.logback:logback-classic:$logbackVersion")

                // transport models
                api(project(":public-transport-timetable-api-v1-jackson"))
                api(project(":public-transport-timetable-mappers-v1"))

                // logs
                api(project(":public-transport-timetable-lib-logging-logback"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
