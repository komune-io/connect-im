plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    Dependencies.Jvm.cache(::implementation)

    Dependencies.Jvm.f2(::implementation)
}
