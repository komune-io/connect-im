plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

dependencies {
	implementation(project(Modules.Api.config))
    implementation(project(Modules.Commons.api))
    implementation(project(Modules.Infra.keycloak))

    implementation(project(Modules.F2.apikeyApi))
    implementation(project(Modules.F2.apikeyLib))
    implementation(project(Modules.F2.organizationApi))
    implementation(project(Modules.F2.organizationLib))
    implementation(project(Modules.F2.privilegeApi))
    implementation(project(Modules.F2.privilegeLib))
    implementation(project(Modules.F2.spaceApi))
    implementation(project(Modules.F2.spaceLib))
    implementation(project(Modules.F2.userApi))
    implementation(project(Modules.F2.userLib))

    implementation(project(Modules.Core.privilegeApi))
    implementation(project(Modules.Core.clientApi))

	Dependencies.Jvm.f2(::implementation)
	Dependencies.Mpp.f2(::implementation)
	Dependencies.Mpp.s2(::implementation)
	Dependencies.Jvm.cucumber(::implementation)

    implementation("io.komune.s2:s2-test-bdd:${Versions.s2}") {
        exclude(group = "io.komune.i2", module = "i2-spring-boot-starter-auth")
    }
//	Dependencies.Jvm.i2AuthClient(::implementation)

	Dependencies.Jvm.testcontainers(::testImplementation)

	implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
}
