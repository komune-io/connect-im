plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
}

dependencies {
    api(project(Modules.Api.config))
    api(project(Modules.Commons.api))

    implementation(project(Modules.Infra.keycloak))
    api(project(Modules.F2.privilegeLib))

    api(project(Modules.Core.clientDomain))
    implementation(project(Modules.Core.clientApi))
}
