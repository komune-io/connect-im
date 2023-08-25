plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    testImplementation(project(Modules.keycloakTestBdd))

    Dependencies.Jvm.Keycloak.adminClient(::testImplementation)
}
