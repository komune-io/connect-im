plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.apikeyDomain))

    implementation(project(Modules.Api.config))

    implementation(project(Modules.Core.clientApi))
    implementation(project(Modules.Core.organizationApi))
    implementation(project(Modules.Core.userApi))

    implementation(project(Modules.Infra.redis))
}
