plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    Dependencies.Jvm.cache(::implementation)

    Dependencies.Jvm.f2(::implementation)
}
