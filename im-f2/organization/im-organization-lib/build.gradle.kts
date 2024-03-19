plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.organizationDomain))

    implementation(project(Modules.Core.organizationApi))
    implementation(project(Modules.Core.privilegeApi))

    implementation(project(Modules.F2.apikeyLib))
    implementation(project(Modules.F2.privilegeLib))
    implementation(project(Modules.F2.userLib))

    implementation(project(Modules.Api.config))
    implementation(project(Modules.Infra.redis))
}
