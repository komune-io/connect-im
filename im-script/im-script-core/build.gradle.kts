plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
}

dependencies {
    api(project(":im-api:api-config"))
    api(project(":im-commons:im-commons-api"))

    implementation(project(":im-infra:im-keycloak"))
    api(project(":im-f2:privilege:im-privilege-lib"))
    api(project(":im-f2:space:im-space-lib"))
    api(project(":im-f2:user:im-user-lib"))

    api(project(":im-core:client-core:im-client-core-domain"))
    implementation(project(":im-core:client-core:im-client-core-api"))

    testImplementation(libs.bundles.junit)
}
