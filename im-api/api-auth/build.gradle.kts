plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation("city.smartb.i2:keycloak-auth-domain:${Versions.i2}")
    api("city.smartb.i2:i2-spring-boot-starter-auth:${Versions.i2}")
}
