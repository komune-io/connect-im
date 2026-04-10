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
	implementation(libs.spring.boot.starter.webflux)
}
