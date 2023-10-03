plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")

    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.Api.config))
    api(project(Modules.Commons.api))

    Dependencies.Jvm.Keycloak.adminClient(::api)
    Dependencies.Jvm.Keycloak.serverSpiPrivate(::api)
}
