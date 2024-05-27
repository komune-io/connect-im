plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.F2.apikeyDomain))
    Dependencies.Mpp.f2Client(::commonMainApi)
}
