plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-f2:privilege:im-privilege-domain"))
    implementation(project(":im-core:privilege-core:im-privilege-core-api"))
}
