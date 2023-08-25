plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.Infra.keycloak))

    api(project(Modules.imCommonsApi))

    api(project(Modules.Script.imScriptFunctionCore))

    api(project(Modules.imKeycloakF2ClientDomain))
    implementation(project(Modules.imKeycloakF2ClientCommand))
    implementation(project(Modules.imKeycloakF2ClientQuery))

    api(project(Modules.imKeycloakF2RealmQuery))
    api(project(Modules.imKeycloakF2RealmDomain))

    api(project(Modules.imKeycloakF2RoleDomain))
    implementation(project(Modules.imKeycloakF2RoleCommand))
    implementation(project(Modules.imKeycloakF2RoleQuery))

    api(project(Modules.imKeycloakF2UserDomain))
    implementation(project(Modules.imKeycloakF2UserCommand))
    implementation(project(Modules.imKeycloakF2UserQuery))
}
