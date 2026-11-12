plugins {
    kotlin("jvm")
    id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
    api(libs.keycloak.server.spi.private)

    testImplementation(libs.bundles.junit)
}
