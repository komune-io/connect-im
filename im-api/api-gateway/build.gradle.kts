plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":im-api:api-config"))
	implementation(project(":im-infra:infra-redis"))

	implementation(project(Modules.S2.imApikeyApi))
	implementation(project(Modules.S2.imOrganizationApi))
	implementation(project(Modules.S2.imRoleApi))
	implementation(project(Modules.S2.imUserApi))
	implementation(project(Modules.S2.imSpaceApi))

	implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
	implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
	imageName.set("smartbcity/im-gateway:${this.project.version}")
}
