plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(":im-f2:organization:im-organization-domain"))
    api(catalogue.client.core)
    api(catalogue.client.ktor)
    api(catalogue.client.ktor.http)
    api(libs.ktor.client.auth)
    implementation(libs.ktor.serialization.jackson)

    testImplementation(libs.bundles.junit)
    testImplementation(kotlin("reflect"))
}
