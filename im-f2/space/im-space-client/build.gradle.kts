plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    id("io.komune.fixers.gradle.npm")
}

dependencies {
    commonMainApi(project(":im-f2:space:im-space-domain"))
    commonMainApi(catalogue.client.core)
    commonMainApi(catalogue.client.ktor)
    commonMainApi(catalogue.client.ktor.http)
    commonMainApi(libs.ktor.client.auth)
}
