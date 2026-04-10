plugins {
    kotlin("jvm")
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    api(libs.keycloak.server.spi.private)
}
