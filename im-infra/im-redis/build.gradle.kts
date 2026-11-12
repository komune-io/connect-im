plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(libs.bundles.spring.cache)

    implementation(catalogue.spring.boot.starter.function.http)

    testImplementation(libs.bundles.junit)
}
