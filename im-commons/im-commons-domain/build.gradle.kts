plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(catalogue.dsl.cqrs)
    commonMainApi(catalogue.client.domain)
    commonMainApi(libs.kotlinx.datetime)
}
