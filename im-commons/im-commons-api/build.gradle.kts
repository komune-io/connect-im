plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(Modules.Commons.domain))
    Dependencies.Jvm.f2(::api)
}
