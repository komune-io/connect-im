plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
}

dependencies {
    api(project(Modules.imApiConfig))
    api(project(Modules.imCommonsApi))

    implementation(project(Modules.Infra.keycloak))
    api(project(Modules.F2.privilegeLib))

    api(project(Modules.Core.clientDomain))
    implementation(project(Modules.Core.clientApi))

    api(project(Modules.imKeycloakF2UserDomain))
    implementation(project(Modules.imKeycloakF2UserCommand))
    implementation(project(Modules.imKeycloakF2UserQuery))
}
