plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.F2.privilegeDomain))
    Dependencies.Mpp.f2Client(::commonMainApi)
    jvmMainImplementation("io.ktor:ktor-serialization-jackson:${Versions.ktor}")
}
