plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("kapt")
}

dependencies {
    api(project(":im-commons:im-commons-api"))
    api(project(":im-commons:im-commons-auth"))
    api(libs.jackson.module.kotlin)

    api(libs.fs.file.client)
    api(catalogue.spring.boot.starter.auth.tenant)
}
