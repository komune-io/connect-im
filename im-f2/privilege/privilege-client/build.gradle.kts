plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.F2.privilegeDomain))
    jvmMainImplementation(project(Modules.Commons.api))
    Dependencies.Mpp.f2Client(::commonMainApi)
}
