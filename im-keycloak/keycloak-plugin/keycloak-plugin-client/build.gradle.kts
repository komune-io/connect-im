plugins {
    kotlin("jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(Modules.keycloakPluginDomain))
    Dependencies.Jvm.ktor(::implementation)
}
