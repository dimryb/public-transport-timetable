plugins {
    kotlin("jvm")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    group = "space.rybakov.public-transport-timetable"
    version = "1.0-SNAPSHOT"

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}

dependencies {
    val kotestVersion: String by project

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
    test {
        systemProperty("kotest.framework.test.severity", "NORMAL")
    }
    create<Test>("test-strict") {
        systemProperty("kotest.framework.test.severity", "MINOR")
        group = "verification"
    }
}