plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(":im-f2:apikey:im-apikey-domain"))
    api(catalogue.client.core)
    api(catalogue.client.ktor)
    api(catalogue.client.ktor.http)
    api(libs.ktor.client.auth)

    testImplementation(libs.bundles.junit)
    testImplementation(kotlin("reflect"))
}
