plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    id("io.komune.fixers.gradle.npm")
}

dependencies {
    commonMainApi(project(Modules.Commons.domain))
    commonMainApi(project(Modules.F2.privilegeDomain))
}
