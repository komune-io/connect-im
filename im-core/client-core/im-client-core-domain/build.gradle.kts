plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    kotlin("plugin.serialization")
}

dependencies {

    commonTestImplementation(kotlin("test"))
}
