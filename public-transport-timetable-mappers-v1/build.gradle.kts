plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":public-transport-timetable-api-v1-jackson"))
    implementation(project(":public-transport-timetable-common"))

    testImplementation(kotlin("test-junit"))
}
