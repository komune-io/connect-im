plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-f2:client:i2-client-domain"))
    api(project(":i2-keycloak:keycloak-f2:user:i2-user-f2-query"))
    api(project(":i2-keycloak:keycloak-f2:commons:i2-commons-api"))
}
