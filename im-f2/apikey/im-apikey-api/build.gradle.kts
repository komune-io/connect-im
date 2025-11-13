plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(Modules.Api.config))

    implementation(project(Modules.Commons.auth))
    implementation(project(Modules.Infra.redis))

    api(project(Modules.F2.apikeyLib))
    api(project(Modules.F2.apikeyDomain))
    implementation(project(Modules.F2.userLib))
}
