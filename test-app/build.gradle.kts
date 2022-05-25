plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot")
	kotlin("plugin.spring")
}

dependencies {
	api("org.springframework.boot:spring-boot-starter:${Versions.springBoot}")

	implementation(project(":im-organization:organization-client"))
	implementation(project(":im-api:api-auth"))

	implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
	implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
}
