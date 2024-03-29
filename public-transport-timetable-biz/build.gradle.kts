plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}
    macosX64 {}
    macosArm64 {}
    //linuxX64 {}

    sourceSets {
        val coroutinesVersion: String by project

        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":public-transport-timetable-common"))
                implementation(project(":public-transport-timetable-stubs"))
                implementation(project(":public-transport-timetable-lib-cor"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(project(":public-transport-timetable-repo-stubs"))
                implementation(project(":public-transport-timetable-repo-tests"))
                implementation(project(":public-transport-timetable-repo-in-memory"))

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
