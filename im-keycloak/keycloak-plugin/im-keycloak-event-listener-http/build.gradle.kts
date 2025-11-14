plugins {
    kotlin("jvm")
    id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
    implementation(project(Modules.Keycloak.pluginDomain))
    Dependencies.Jvm.ktor(::implementation)
}
