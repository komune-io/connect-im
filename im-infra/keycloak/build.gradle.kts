plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")

    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.imApiConfig))
    api(project(Modules.imCommonsApi))
    api(project(Modules.imKeycloakF2CommonsDomain))

    Dependencies.Jvm.Keycloak.adminClient(::api)
}
