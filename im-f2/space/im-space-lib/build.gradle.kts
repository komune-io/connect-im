plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.spaceDomain))

    implementation(project(Modules.Core.clientApi))

    implementation(project(Modules.Api.config))
    api(project(Modules.Infra.redis))
    api(project(Modules.Core.commons))
}
