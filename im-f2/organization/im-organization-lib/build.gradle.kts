plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-f2:organization:im-organization-domain"))

    implementation(project(":im-core:organization-core:im-organization-core-api"))
    implementation(project(":im-core:privilege-core:im-privilege-core-api"))

    implementation(project(":im-f2:apikey:im-apikey-lib"))
    implementation(project(":im-f2:privilege:im-privilege-lib"))
    implementation(project(":im-f2:user:im-user-lib"))

    implementation(project(":im-api:api-config"))
    implementation(project(":im-infra:im-redis"))

    implementation(catalogue.client.ktor)
    implementation(catalogue.client.ktor.http)
    implementation(libs.ktor.client.auth)
    implementation(libs.bundles.ktor.client)
}
