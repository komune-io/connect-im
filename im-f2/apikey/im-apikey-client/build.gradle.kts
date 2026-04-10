plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(":im-f2:apikey:im-apikey-domain"))
    commonMainApi(catalogue.client.core)
    commonMainApi(catalogue.client.ktor)
    commonMainApi(catalogue.client.ktor.http)
    commonMainApi(libs.ktor.client.auth)
}
