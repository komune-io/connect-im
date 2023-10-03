plugins {
    kotlin("jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    Dependencies.Jvm.Keycloak.serverSpiPrivate(::api)
}
