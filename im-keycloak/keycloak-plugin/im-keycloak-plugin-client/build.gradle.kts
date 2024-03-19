plugins {
    kotlin("jvm")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    api(project(Modules.Keycloak.pluginDomain))
    Dependencies.Jvm.ktor(::implementation)
}
