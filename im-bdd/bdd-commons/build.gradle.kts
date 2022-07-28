plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

dependencies {
//	implementation(project(":platform:api:api-commons"))
//	implementation(project(":platform:api:api-config"))
//	implementation(project(":platform:infra:postgresql"))

//	implementation(project(":platform:s2:eligibility:eligibility-api"))
//	implementation(project(":platform:s2:project:project-api"))

	Dependencies.Jvm.f2(::implementation)
	Dependencies.Mpp.f2(::implementation)
	Dependencies.Mpp.s2(::implementation)
	Dependencies.Jvm.cucumber(::implementation)

	testImplementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
	city.smartb.gradle.dependencies.FixersDependencies.Jvm.Test.junit(::implementation)
}
