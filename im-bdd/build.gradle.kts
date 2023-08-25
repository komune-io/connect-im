plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

dependencies {
	implementation(project(Modules.imApiConfig))
    implementation(project(Modules.imCommonsApi))
    implementation(project(Modules.Infra.keycloak))

    implementation(project(Modules.F2.imApikeyApi))
    implementation(project(Modules.F2.imApikeyLib))

    implementation(project(Modules.F2.imOrganizationApi))
    implementation(project(Modules.F2.imOrganizationLib))

    implementation(project(Modules.F2.imUserApi))
    implementation(project(Modules.F2.imUserLib))

    implementation(project(Modules.F2.privilegeApi))
    implementation(project(Modules.F2.privilegeLib))

    implementation(project(Modules.Core.privilegeApi))

	Dependencies.Jvm.f2(::implementation)
	Dependencies.Mpp.f2(::implementation)
	Dependencies.Mpp.s2(::implementation)
	Dependencies.Jvm.cucumber(::implementation)
	Dependencies.Jvm.i2AuthClient(::implementation)

	Dependencies.Jvm.testcontainers(::testImplementation)

	implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
}
