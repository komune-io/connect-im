plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(Modules.Commons.domain))
    api("io.komune.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")
    Dependencies.Jvm.f2(::api)
}
