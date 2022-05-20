plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":im-api:api-config"))

	implementation(project(":im-organization:organization-api"))
	implementation(project(":im-role:role-api"))
	implementation(project(":im-user:user-api"))
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
	imageName = "smartbcity/im-gateway:${this.project.version}"
}
