plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm { }
    //linuxX64 { }
    macosX64 { }
    macosArm64 { }

    sourceSets {
        val coroutinesVersion: String by project
        val datetimeVersion: String by project

        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":public-transport-timetable-api-log1"))
                implementation(project(":public-transport-timetable-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

    }
}
