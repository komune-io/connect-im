plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.userDomain))

    implementation(project(Modules.F2.userLib))
    implementation(project(Modules.Core.mfaApi))

    implementation(project(":im-api:api-config"))
    implementation(project(Modules.Commons.auth))
}
