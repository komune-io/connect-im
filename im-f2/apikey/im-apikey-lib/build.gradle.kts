plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.apikeyDomain))

    implementation(project(Modules.Api.config))

    implementation(project(Modules.F2.privilegeLib))

    implementation(project(Modules.Core.clientApi))
    implementation(project(Modules.Core.organizationApi))
    implementation(project(Modules.Core.privilegeApi))
    implementation(project(Modules.Core.userApi))

    implementation(project(Modules.Infra.redis))
}
