plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.organizationLib))

    implementation(project(Modules.Api.config))
    implementation(project(Modules.Commons.auth))
    implementation(project(Modules.Infra.redis))

    implementation(project(Modules.F2.apikeyLib))
    implementation(project(Modules.F2.userLib))
    implementation(project(Modules.F2.privilegeLib))
}
