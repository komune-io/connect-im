plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")

    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-keycloak:keycloak-auth:keycloak-auth-domain"))
    api(project(":im-keycloak:keycloak-utils"))

    api("org.keycloak:keycloak-admin-client:${Versions.keycloak}")

}
