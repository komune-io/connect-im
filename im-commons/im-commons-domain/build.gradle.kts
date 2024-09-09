plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi("io.komune.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("io.komune.f2:f2-client-domain:${Versions.f2}")
    Dependencies.Mpp.datetime(::commonMainApi)
}
