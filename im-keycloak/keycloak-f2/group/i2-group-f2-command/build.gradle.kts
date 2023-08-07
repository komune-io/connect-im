plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-f2:group:i2-group-domain"))
    api(project(":i2-keycloak:keycloak-f2:commons:i2-commons-api"))
}
