plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(Modules.imCommonsDomain))
    api(project(Modules.F2.imApikeyDomain))
}
