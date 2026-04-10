plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-infra:im-keycloak"))

    api(project(":im-commons:im-commons-api"))

    api(project(":im-script:im-script-core"))

    implementation(project(":im-core:client-core:im-client-core-api"))

    implementation(project(":im-f2:privilege:im-privilege-lib"))
    implementation(project(":im-f2:space:im-space-lib"))
    implementation(project(":im-f2:user:im-user-lib"))

    implementation(project(":im-keycloak:keycloak-plugin:im-keycloak-plugin-domain"))

    testImplementation(libs.bundles.junit)
    testImplementation(project(":im-bdd"))
    testImplementation(project(":im-f2:apikey:im-apikey-lib"))
}
