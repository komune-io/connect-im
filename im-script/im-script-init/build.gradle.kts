plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(Modules.Infra.keycloak))
    implementation(project(Modules.Commons.api))
    implementation(project(Modules.Script.core))
    implementation(project(Modules.Core.clientApi))
}
