plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.spaceDomain))

    implementation(project(Modules.Core.clientApi))
    api(project(Modules.Core.mfaApi))
    implementation(project(Modules.Api.config))

    api(project(Modules.Infra.redis))
    api(project(Modules.Core.commons))
}
