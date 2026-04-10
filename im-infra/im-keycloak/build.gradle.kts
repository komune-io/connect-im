plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")

    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-api:api-config"))
    api(project(":im-commons:im-commons-api"))

    api(libs.keycloak.admin.client)
    api(libs.keycloak.server.spi.private)
}
