plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-f2:apikey:im-apikey-domain"))

    implementation(project(":im-api:api-config"))

    implementation(project(":im-f2:privilege:im-privilege-lib"))

    implementation(project(":im-core:client-core:im-client-core-api"))
    implementation(project(":im-core:organization-core:im-organization-core-api"))
    implementation(project(":im-core:privilege-core:im-privilege-core-api"))
    implementation(project(":im-core:user-core:im-user-core-api"))

    implementation(project(":im-infra:im-redis"))
}
