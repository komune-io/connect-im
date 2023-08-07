plugins {
    kotlin("jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api("org.keycloak:keycloak-server-spi-private:${Versions.keycloak}")
}
