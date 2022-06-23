plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("city.smartb.i2:keycloak-auth-domain:${Versions.i2}")
    api("city.smartb.i2:i2-spring-boot-starter-auth:${Versions.i2}")

    // to check if really needed
    implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
    implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
}
