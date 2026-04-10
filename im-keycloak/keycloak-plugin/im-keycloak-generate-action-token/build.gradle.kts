plugins {
    kotlin("jvm")
    id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
    implementation(project(":im-keycloak:keycloak-plugin:im-keycloak-plugin-domain"))
}
