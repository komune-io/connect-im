plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")

    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.imKeycloakAuthDomain))
    api(project(Modules.imKeycloakUtils))

    Dependencies.Jvm.Keycloak.adminClient(::api)

}
