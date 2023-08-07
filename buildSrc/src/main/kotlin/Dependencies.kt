import city.smartb.gradle.dependencies.FixersDependencies
import city.smartb.gradle.dependencies.FixersPluginVersions
import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.Scope
import city.smartb.gradle.dependencies.add

object Framework {
	val fixers = FixersPluginVersions.fixers
	val connect = "experimental-SNAPSHOT"
}

object PluginVersions {
	val fixers = Framework.fixers
	const val springBoot = FixersPluginVersions.springBoot
	const val kotlin = FixersPluginVersions.kotlin

	const val shadowJar = "7.1.2"
}

object Versions {
	val f2 = Framework.fixers
	val i2 = Framework.fixers
	val s2 = Framework.fixers

	val fs = Framework.connect

	const val ktor = FixersVersions.Kotlin.ktor

	const val springBoot = PluginVersions.springBoot
	const val springData = FixersVersions.Spring.data

	const val kdatetime = "0.1.1"
	const val keycloak = "18.0.0"
	const val mockito = "4.6.1"
	const val testcontainers = "1.18.0"

	const val slf4j = FixersVersions.Logging.slf4j
}

object Dependencies {
	object Jvm {
		fun f2(scope: Scope) = scope.add(
			"city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}"
		)

		fun f2Function(scope: Scope) = scope.add(
			"city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}"
		)

		fun slf4j(scope: Scope) = scope.add(
			"org.slf4j:slf4j-api:${Versions.slf4j}",
		)

		object Fs {
			fun client(scope: Scope) = scope.add(
				"city.smartb.fs:file-client:${Versions.fs}"
			)
		}

		fun i2Auth(scope: Scope) = scope.add(
			"city.smartb.i2:i2-spring-boot-starter-auth:${Versions.i2}"
		)

		fun i2AuthClient(scope: Scope) = scope.add(
			"city.smartb.i2:keycloak-auth-client:${Versions.i2}"
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

		fun testcontainers(scope: Scope) = scope.add(
			"org.testcontainers:junit-jupiter:${Versions.testcontainers}"
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
