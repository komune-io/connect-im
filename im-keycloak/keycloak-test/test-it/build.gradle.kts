plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    testImplementation(project(Modules.keycloakTestBdd))
    testImplementation(project(":im-keycloak:keycloak-auth:keycloak-auth-client"))

    Dependencies.Jvm.Keycloak.adminClient(::testImplementation)
}
