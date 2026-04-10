plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-core:client-core:im-client-core-domain"))

    implementation(project(":im-api:api-config"))
    implementation(project(":im-keycloak:keycloak-plugin:im-keycloak-plugin-domain"))
}
