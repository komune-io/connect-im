plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-infra:im-keycloak"))
    api(project(":im-infra:im-redis"))

    api(project(":im-commons:im-commons-api"))
    api(project(":im-script:im-script-core"))

    implementation(project(":im-f2:apikey:im-apikey-lib"))
    implementation(project(":im-f2:organization:im-organization-lib"))
    implementation(project(":im-f2:space:im-space-lib"))
    implementation(project(":im-f2:user:im-user-lib"))

    testImplementation(libs.bundles.junit)
    testImplementation(project(":im-bdd"))
    testImplementation(project(":im-script:im-script-space-create"))
}
