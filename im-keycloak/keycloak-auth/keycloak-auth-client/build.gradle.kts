plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")

    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.imKeycloakAuthDomain))
    api(project(Modules.imKeycloakUtils))
    api(project(Modules.imCommonsApi))

    Dependencies.Jvm.Keycloak.adminClient(::api)

}
