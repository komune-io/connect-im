import city.smartb.gradle.dependencies.FixersDependencies
import city.smartb.gradle.dependencies.FixersPluginVersions
import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.Scope
import city.smartb.gradle.dependencies.add

object PluginVersions {
	val fixers = FixersPluginVersions.fixers
	const val d2 = "0.9.0"
	const val springBoot = FixersPluginVersions.springBoot
	const val kotlin = FixersPluginVersions.kotlin
}

object Versions {
	val i2 = "next-SNAPSHOT"
	val f2 = PluginVersions.fixers
	val fs = "0.9.1"
	val s2 = PluginVersions.fixers
	const val ktor = "2.0.3"

	const val springBoot = PluginVersions.springBoot
	const val springOauth2 = "5.6.0"

	const val kdatetime = "0.1.1"
	const val keycloak = "18.0.0"

	const val testcontainers = FixersVersions.Test.testcontainers
}

object Dependencies {
	object Jvm {
		fun f2(scope: Scope) = scope.add(
			"city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}"
		)
		fun ktor(scope: Scope) = scope.add(
			"io.ktor:ktor-client-core:${Versions.ktor}",
			"io.ktor:ktor-client-content-negotiation:${Versions.ktor}",
			"io.ktor:ktor-client-cio:${Versions.ktor}",
			"io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}",
			"io.ktor:ktor-serialization-jackson:${Versions.ktor}"
		)


		fun cucumber(scope: Scope) = FixersDependencies.Jvm.Test.cucumber(scope).add(
			"io.cucumber:cucumber-spring:${FixersVersions.Test.cucumber}"
		)
	}

	object Js {
		fun ktor(scope: Scope) = scope.add(
			"io.ktor:ktor-client-core-js:${Versions.ktor}",
			"io.ktor:ktor-client-json-js:${Versions.ktor}"
		)
	}

	object Mpp {
		fun f2(scope: Scope) = scope.add(
			"city.smartb.f2:f2-dsl-cqrs:${Versions.f2}",
			"city.smartb.f2:f2-dsl-function:${Versions.f2}"
		)

		fun ktor(scope: Scope) = scope.add(
			"io.ktor:ktor-client-core:${Versions.ktor}",
			"io.ktor:ktor-client-serialization:${Versions.ktor}"
		)
		fun s2(scope: Scope) = scope.add(
			"city.smartb.s2:s2-automate-dsl:${Versions.s2}",
			"city.smartb.s2:s2-sourcing-dsl:${Versions.s2}"
		)
	}
}
