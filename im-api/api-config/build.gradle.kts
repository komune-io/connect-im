plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("kapt")
}

dependencies {
    api(project(Modules.Commons.api))
    api(project(Modules.Commons.auth))
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    Dependencies.Jvm.Fs.client(::api)
    Dependencies.Jvm.f2Auth(::api)
}
