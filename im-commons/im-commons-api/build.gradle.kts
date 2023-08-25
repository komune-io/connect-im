plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(Modules.imCommonsDomain))
    api("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")
    Dependencies.Jvm.f2(::api)
    Dependencies.Mpp.f2Client(::api)
}
