plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    id("io.komune.fixers.gradle.npm")
}

dependencies {
    commonMainApi(project(Modules.F2.spaceDomain))
    Dependencies.Mpp.f2Client(::commonMainApi)
}
