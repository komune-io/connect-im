plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")

    kotlin("plugin.spring")
}

dependencies {
    jvmMainApi(project(Modules.imCommonsApi))
    jvmMainApi(project(Modules.imApiConfig))

    Dependencies.Jvm.Keycloak.adminClient(::jvmMainApi)
}
