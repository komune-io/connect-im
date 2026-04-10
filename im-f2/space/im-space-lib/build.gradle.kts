plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-f2:space:im-space-domain"))

    implementation(project(":im-core:client-core:im-client-core-api"))
    api(project(":im-core:mfa-core:im-mfa-core-api"))
    implementation(project(":im-api:api-config"))

    api(project(":im-infra:im-redis"))
    api(project(":im-core:im-commons-core"))
}
