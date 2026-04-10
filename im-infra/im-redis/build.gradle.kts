plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(libs.bundles.spring.cache)

    implementation(catalogue.spring.boot.starter.function.http)
}
