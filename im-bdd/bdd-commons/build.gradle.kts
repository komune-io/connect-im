plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

dependencies {
	implementation(project(":im-api:api-config"))
	implementation(project(":im-organization:organization-api"))
	implementation(project(":im-role:role-api"))
	implementation(project(":im-user:user-api"))

	Dependencies.Jvm.f2(::implementation)
	Dependencies.Mpp.f2(::implementation)
	Dependencies.Mpp.s2(::implementation)
	Dependencies.Jvm.cucumber(::implementation)
}
