plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-infra:im-keycloak"))
    implementation(project(":im-commons:im-commons-api"))
    implementation(project(":im-script:im-script-core"))
    implementation(project(":im-core:client-core:im-client-core-api"))
}
