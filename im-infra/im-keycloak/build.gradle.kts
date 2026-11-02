plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")

    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-api:api-config"))
    api(project(":im-commons:im-commons-api"))
    implementation(project(":im-keycloak:keycloak-plugin:im-keycloak-plugin-domain"))

    api(libs.keycloak.admin.client)
    api(libs.keycloak.server.spi.private)

    testImplementation(libs.bundles.junit)
}
