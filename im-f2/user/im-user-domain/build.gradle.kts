plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    id("io.komune.fixers.gradle.npm")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.Core.userDomain))
    commonMainApi(project(Modules.Core.mfaDomain))
    commonMainApi(project(Modules.F2.organizationDomain))
}
