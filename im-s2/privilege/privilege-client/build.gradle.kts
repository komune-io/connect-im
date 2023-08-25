plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.S2.privilegeDomain))
    jvmMainImplementation(project(Modules.imCommonsApi))
    Dependencies.Mpp.f2Client(::commonMainApi)
}
