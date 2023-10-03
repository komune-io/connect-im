plugins {
    kotlin("jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(Modules.Keycloak.pluginDomain))
    Dependencies.Jvm.ktor(::implementation)
}
