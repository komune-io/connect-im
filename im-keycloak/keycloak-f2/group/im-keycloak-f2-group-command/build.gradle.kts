plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.imKeycloakF2GroupDomain))
    api(project(Modules.imKeycloakF2CommonsApi))
}
