plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("kapt")
}

dependencies {
    api(project(":im-commons:im-commons-api"))
    api(project(":im-commons:im-commons-auth"))
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    api("city.smartb.fs:file-client:${Versions.fs}")
    api("city.smartb.i2:i2-spring-boot-starter-auth:${Versions.i2}")
}
