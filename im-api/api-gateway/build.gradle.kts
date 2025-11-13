plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(Modules.Api.config))
	implementation(project(Modules.Infra.redis))

	implementation(project(Modules.F2.apikeyApi))
	implementation(project(Modules.F2.organizationApi))
	implementation(project(Modules.F2.privilegeApi))
	implementation(project(Modules.F2.userApi))
	implementation(project(Modules.F2.spaceApi))

	Dependencies.Jvm.f2Http(::implementation)
	Dependencies.Jvm.springBootWebflux(::implementation)
}
