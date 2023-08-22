plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.imKeycloakF2CommonsDomain))


    Dependencies.Jvm.f2Function(::api)
    Dependencies.Jvm.slf4j(::api)
}
