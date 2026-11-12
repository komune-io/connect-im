plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-infra:im-keycloak"))
    implementation(project(":im-infra:im-redis"))

    testImplementation(libs.bundles.junit)
}
