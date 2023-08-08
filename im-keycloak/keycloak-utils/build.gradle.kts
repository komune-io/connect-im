plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {

    api(project(Modules.imKeycloakF2CommonsDomain))
    Dependencies.Jvm.Keycloak.adminClient(::api)
}
