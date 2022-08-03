plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":im-api:api-config"))
	implementation(project(":im-infra:infra-redis"))

	implementation(project(":im-organization:organization-api"))
	implementation(project(":im-role:role-api"))
	implementation(project(":im-user:user-api"))

	implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
	implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")

	implementation("city.smartb.i2:i2-spring-boot-starter-auth:${Versions.i2}")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
	imageName = "smartbcity/im-gateway:${this.project.version}"
}
