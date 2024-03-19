plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.Commons.domain))
    commonMainApi(project(Modules.F2.privilegeDomain))
}
