plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

dependencies {
	implementation(project(":im-api:api-config"))
    implementation(project(":im-commons:im-commons-api"))
    implementation(project(":im-infra:im-keycloak"))

    implementation(project(":im-f2:apikey:im-apikey-api"))
    implementation(project(":im-f2:apikey:im-apikey-lib"))
    implementation(project(":im-f2:organization:im-organization-api"))
    implementation(project(":im-f2:organization:im-organization-lib"))
    implementation(project(":im-f2:privilege:im-privilege-api"))
    implementation(project(":im-f2:privilege:im-privilege-lib"))
    implementation(project(":im-f2:space:im-space-api"))
    implementation(project(":im-f2:space:im-space-lib"))
    implementation(project(":im-f2:user:im-user-api"))
    implementation(project(":im-f2:user:im-user-lib"))

    implementation(project(":im-core:privilege-core:im-privilege-core-api"))
    implementation(project(":im-core:client-core:im-client-core-api"))

	implementation(catalogue.spring.boot.starter.function.http)
	implementation(catalogue.dsl.cqrs)
	implementation(catalogue.dsl.function)
	implementation(libs.s2.automate.dsl)
	implementation(libs.s2.test.bdd)
	implementation(libs.bundles.cucumber)
	implementation(libs.cucumber.spring)
	implementation(libs.spring.boot.starter.test)
	implementation(libs.bundles.junit)
	implementation(libs.mockito.core)

	implementation(libs.testcontainers.junit.jupiter)
	implementation(libs.spring.boot.starter.webflux)
}
