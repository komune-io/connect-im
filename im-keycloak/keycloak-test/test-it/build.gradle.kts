plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    testImplementation(project(":i2-keycloak:keycloak-test:test-bdd"))
    testImplementation(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))

    testImplementation("org.keycloak:keycloak-admin-client:${Versions.keycloak}")
}
