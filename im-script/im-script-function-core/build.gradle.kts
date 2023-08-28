plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
}

dependencies {
    api(project(Modules.imCommonsApi))

    implementation(project(Modules.Infra.keycloak))
    api(project(Modules.F2.privilegeLib))

    api(project(Modules.imKeycloakF2ClientDomain))
    implementation(project(Modules.imKeycloakF2ClientCommand))
    implementation(project(Modules.imKeycloakF2ClientQuery))

    api(project(Modules.imKeycloakF2RealmQuery))
    api(project(Modules.imKeycloakF2RealmDomain))

    api(project(Modules.imKeycloakF2UserDomain))
    implementation(project(Modules.imKeycloakF2UserCommand))
    implementation(project(Modules.imKeycloakF2UserQuery))
}
