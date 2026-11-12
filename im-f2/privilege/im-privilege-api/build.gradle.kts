plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-f2:privilege:im-privilege-lib"))

    testImplementation(libs.bundles.junit)
}
