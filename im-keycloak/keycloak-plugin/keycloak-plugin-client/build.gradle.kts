plugins {
    kotlin("jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":im-keycloak:keycloak-plugin:keycloak-plugin-domain"))
    Dependencies.Jvm.ktor(::implementation)
}
