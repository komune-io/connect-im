plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-f2:privilege:im-privilege-domain"))
    implementation(project(":im-core:privilege-core:im-privilege-core-api"))

    testImplementation(libs.bundles.junit)
}
