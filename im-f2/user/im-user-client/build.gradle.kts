plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    id("io.komune.fixers.gradle.npm")
}

dependencies {
    commonMainApi(project(Modules.F2.userDomain))
    Dependencies.Mpp.f2Client(::commonMainApi)
    jvmMainImplementation("io.ktor:ktor-serialization-jackson:${Versions.ktor}")
}
