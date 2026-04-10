plugins {
    kotlin("jvm")
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    api(project(":im-keycloak:keycloak-plugin:im-keycloak-plugin-domain"))
    implementation(libs.bundles.ktor.client)
}
