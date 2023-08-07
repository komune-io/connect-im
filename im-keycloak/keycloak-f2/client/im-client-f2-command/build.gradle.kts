plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.spring")
}

dependencies {
	api(project(":im-keycloak:keycloak-f2:client:im-client-domain"))

	api(project(":im-keycloak:keycloak-f2:commons:im-commons-api"))
	api(project(":im-keycloak:keycloak-auth:keycloak-auth-client"))

	api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")
}
