plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-core:user-core:im-user-core-domain"))
    implementation(project(":im-infra:im-redis"))
}
