plugins {
    kotlin("jvm")
    id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
    implementation(project(":im-keycloak:keycloak-plugin:im-keycloak-plugin-domain"))
    compileOnly(libs.freemarker)

    testImplementation(libs.bundles.junit)
    testImplementation(libs.bundles.keycloak.all)
}
