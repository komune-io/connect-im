plugins {
    kotlin("jvm")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    Dependencies.Jvm.Keycloak.serverSpiPrivate(::api)
}
