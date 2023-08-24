plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.S2.privilegeDomain))

    implementation(project(Modules.imApiConfig))
    implementation(project(Modules.imKeycloakF2RoleCommand))
}
