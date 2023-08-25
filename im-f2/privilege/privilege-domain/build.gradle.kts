plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(Modules.Core.privilegeDomain))
    commonMainApi(project(Modules.imCommonsDomain))

    Dependencies.Mpp.f2(::commonMainApi)
}
