plugins {
    kotlin("jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":i2-keycloak:keycloak-plugin:keycloak-plugin-domain"))
    Dependencies.Jvm.ktor(::implementation)
}
