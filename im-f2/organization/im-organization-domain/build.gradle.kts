plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    id("io.komune.fixers.gradle.npm")
    kotlin("plugin.serialization")
}

dependencies {
    commonMainApi(project(":im-core:organization-core:im-organization-core-domain"))
    commonMainApi(project(":im-f2:privilege:im-privilege-domain"))

    commonMainApi(project(":im-commons:im-commons-domain"))
}
