plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.Infra.keycloak))

    api(project(Modules.imCommonsApi))

    api(project(Modules.Script.core))

    implementation(project(Modules.Core.clientApi))

    implementation(project(Modules.F2.spaceApi))

    api(project(Modules.imKeycloakF2RoleDomain))
    implementation(project(Modules.imKeycloakF2RoleCommand))
    implementation(project(Modules.imKeycloakF2RoleQuery))

    api(project(Modules.imKeycloakF2UserDomain))
    implementation(project(Modules.imKeycloakF2UserCommand))
    implementation(project(Modules.imKeycloakF2UserQuery))
}
