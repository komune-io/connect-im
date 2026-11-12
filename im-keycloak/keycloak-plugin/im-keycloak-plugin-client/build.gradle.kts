plugins {
    kotlin("jvm")
    id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
    api(project(":im-keycloak:keycloak-plugin:im-keycloak-plugin-domain"))
    implementation(libs.bundles.ktor.client)

    testImplementation(libs.bundles.junit)
}
