plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-keycloak:keycloak-auth:keycloak-auth-client"))

    api(project(Modules.imCommonsApi))

    api(project(Modules.imKeycloakF2ClientDomain))
    implementation(project(Modules.imKeycloakF2ClientCommand))
    implementation(project(Modules.imKeycloakF2ClientQuery))

    api(project(Modules.imKeycloakF2RealmDomain))
    implementation(project(Modules.imKeycloakF2RealmCommand))
    implementation(project(Modules.imKeycloakF2RealmQuery))

    api(project(Modules.imKeycloakF2RoleDomain))
    implementation(project(Modules.imKeycloakF2RoleCommand))
    implementation(project(Modules.imKeycloakF2RoleQuery))

    api(project(Modules.imKeycloakF2UserDomain))
    implementation(project(Modules.imKeycloakF2UserCommand))
    implementation(project(Modules.imKeycloakF2UserQuery))
}
