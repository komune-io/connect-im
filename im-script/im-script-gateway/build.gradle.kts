plugins {
    id("org.springframework.boot")
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
//    id("org.graalvm.buildtools.native")
}

dependencies {
    implementation(project(":im-script:im-script-init"))
    implementation(project(":im-script:im-script-space-config"))
    implementation(project(":im-script:im-script-space-create"))

    implementation(catalogue.spring.boot.starter.function)

    testImplementation(libs.bundles.junit)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    builder = "docker.io/paketobuildpacks/builder-noble-java-tiny:${libs.versions.paketoBuilder.get()}"
    buildpacks = listOf(
        "urn:cnb:builder:paketo-buildpacks/java",
        "docker.io/paketobuildpacks/health-checker:${libs.versions.paketoHealthChecker.get()}",
    )
}
