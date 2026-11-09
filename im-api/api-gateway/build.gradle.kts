plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":im-api:api-config"))
	implementation(project(":im-infra:im-redis"))

	implementation(project(":im-f2:apikey:im-apikey-api"))
	implementation(project(":im-f2:organization:im-organization-api"))
	implementation(project(":im-f2:privilege:im-privilege-api"))
	implementation(project(":im-f2:user:im-user-api"))
	implementation(project(":im-f2:space:im-space-api"))

	implementation(catalogue.spring.boot.starter.function.http)
	implementation(catalogue.spring.boot.exception.http.webflux)
	implementation(libs.spring.boot.starter.webflux)

    testImplementation(libs.bundles.junit)
    testImplementation(libs.spring.boot.starter.test)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    builder = "docker.io/paketobuildpacks/builder-noble-java-tiny:${libs.versions.paketoBuilder.get()}"
    buildpacks = listOf(
        "urn:cnb:builder:paketo-buildpacks/java",
        "docker.io/paketobuildpacks/health-checker:${libs.versions.paketoHealthChecker.get()}",
    )
}
