plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(":im-commons:im-commons-domain"))
    api(catalogue.spring.boot.starter.function.http)
}
