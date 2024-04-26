plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
    id("io.komune.fixers.gradle.npm")
}

dependencies {
    commonMainApi(project(Modules.Core.privilegeDomain))
    commonMainApi(project(Modules.Commons.domain))
}
