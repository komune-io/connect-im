plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.Core.clientDomain))

    implementation(project(Modules.Api.config))
    implementation(project(Modules.Keycloak.pluginDomain))
}
