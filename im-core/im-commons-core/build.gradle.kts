plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(Modules.Infra.keycloak))
    implementation(project(Modules.Infra.redis))
}
