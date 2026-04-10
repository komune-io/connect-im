plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    id("io.komune.fixers.gradle.npm")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(":im-core:user-core:im-user-core-domain"))
    commonMainApi(project(":im-core:mfa-core:im-mfa-core-domain"))
    commonMainApi(project(":im-f2:organization:im-organization-domain"))
}
