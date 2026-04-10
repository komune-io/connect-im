plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
    id("io.komune.fixers.gradle.npm")
}

dependencies {
    commonMainApi(project(":im-f2:user:im-user-domain"))
    commonMainApi(catalogue.client.core)
    commonMainApi(catalogue.client.ktor)
    commonMainApi(catalogue.client.ktor.http)
    commonMainApi(libs.ktor.client.auth)
}
