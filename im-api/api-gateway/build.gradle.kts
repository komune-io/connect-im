plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":im-api:api-config"))
	implementation(project(Modules.Infra.redis))

	implementation(project(Modules.F2.apikeyApi))
	implementation(project(Modules.F2.organizationApi))
	implementation(project(Modules.F2.privilegeApi))
	implementation(project(Modules.F2.userApi))
	implementation(project(Modules.F2.spaceApi))

	implementation("io.komune.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
	implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {}
