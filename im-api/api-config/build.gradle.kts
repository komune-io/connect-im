plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
}

dependencies {
    api(project(":im-commons:im-commons-api"))
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    api("city.smartb.i2:i2-spring-boot-starter-auth:${Versions.i2}")
}
