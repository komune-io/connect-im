plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-core:organization-core:im-organization-core-domain"))
    api(project(":im-core:mfa-core:im-mfa-core-domain"))
    implementation(project(":im-infra:im-redis"))

    testImplementation(libs.bundles.junit)
}
