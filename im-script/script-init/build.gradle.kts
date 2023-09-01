plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(Modules.Infra.keycloak))
    implementation(project(Modules.imCommonsApi))
    implementation(project(Modules.Script.core))
    implementation(project(Modules.Core.clientApi))
}
