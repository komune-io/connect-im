import city.smartb.gradle.dependencies.FixersDependencies
import city.smartb.gradle.dependencies.FixersPluginVersions
import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.Scope
import city.smartb.gradle.dependencies.add

object Framework {
	val fixers = FixersPluginVersions.fixers
	val connect =  "experimental-SNAPSHOT"
}


object PluginVersions {
	val fixers = Framework.fixers
	const val springBoot = FixersPluginVersions.springBoot
	const val kotlin = FixersPluginVersions.kotlin
}

object Versions {
	val f2 = Framework.fixers
	val s2 = Framework.fixers
	val i2 = Framework.fixers

	val fs = Framework.fixers

	const val ktor = FixersVersions.Kotlin.ktor

	const val springBoot = PluginVersions.springBoot
	const val springData = FixersVersions.Spring.data

	const val kdatetime = "0.1.1"
	const val keycloak = "18.0.0"
	const val mockito = "4.6.1"
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

		fun cucumber(scope: Scope) = FixersDependencies.Jvm.Test.cucumber(scope)
			.add(
				"io.cucumber:cucumber-spring:${FixersVersions.Test.cucumber}",
				"org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}"
			)
			.also(::junit)
			.also(::mockito)

		fun junit(scope: Scope) = FixersDependencies.Jvm.Test.junit(scope)

		fun cache(scope: Scope) = scope.add(
			"org.springframework.boot:spring-boot-starter-cache:${Versions.springBoot}",
			"org.springframework.data:spring-data-redis:${Versions.springData}",
		)

		fun mockito(scope: Scope) = scope.add(
			"org.mockito:mockito-core:${Versions.mockito}"
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
			"city.smartb.s2:s2-automate-dsl:${Versions.s2}"
		)
	}
}
