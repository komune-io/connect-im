plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.Infra.keycloak))
    api(project(Modules.Infra.redis))

    api(project(Modules.Commons.api))
    api(project(Modules.Script.core))

    implementation(project(Modules.F2.apikeyLib))
    implementation(project(Modules.F2.organizationLib))
    implementation(project(Modules.F2.spaceLib))
    implementation(project(Modules.F2.userLib))
}
