plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.imKeycloakF2UserDomain))
    implementation(project(Modules.imKeycloakF2RoleQuery))
    api(project(Modules.imKeycloakF2CommonsApi))
}
