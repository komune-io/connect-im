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

		object Keycloak {
			fun adminClient(scope: Scope) = scope.add(
				"org.keycloak:keycloak-admin-client:${Versions.keycloak}"
			)
		}
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

		fun datetime(scope: Scope) = scope.add(
			"org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kdatetime}"
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

object Modules {
	object S2 {
		val imOrganizationApi = ":im-s2:im-organization:organization-api"
		val imOrganizationDomain = ":im-s2:im-organization:organization-domain"
		val imOrganizationClient = ":im-s2:im-organization:organization-client"
		val imOrganizationLib = ":im-s2:im-organization:organization-lib"

		val imUserApi = ":im-s2:im-user:user-api"
		val imUserDomain = ":im-s2:im-user:user-domain"
		val imUserClient = ":im-s2:im-user:user-client"
		val imUserLib = ":im-s2:im-user:user-lib"

		val imApikeyApi = ":im-s2:im-apikey:apikey-api"
		val imApikeyDomain = ":im-s2:im-apikey:apikey-domain"
		val imApikeyClient = ":im-s2:im-apikey:apikey-client"
		val imApikeyLib = ":im-s2:im-apikey:apikey-lib"

		val imRoleApi = ":im-s2:im-role:role-api"
		val imRoleDomain = ":im-s2:im-role:role-domain"
		val imRoleClient = ":im-s2:im-role:role-client"
	}

	object Script {
		val imScriptGateway = ":im-script:im-script-gateway"
		val imScriptConfig = ":im-script:im-script-gateway-configuration"

		val imKeycloakF2ScriptConfigCommand = ":im-script:im-script-function-config"
		val imKeycloakF2ScriptInitCommand = ":im-script:im-script-function-init"
	}

	val imCommonsApi = ":im-commons:im-commons-api"
	val imCommonsAuth = ":im-commons:im-commons-auth"
	val imCommonsDomain = ":im-commons:im-commons-domain"

	val imKeycloakUtils = ":im-keycloak:keycloak-utils"

	val imKeycloakAuthClient = ":im-keycloak:keycloak-auth:keycloak-auth-client"
	val imKeycloakAuthDomain = ":im-keycloak:keycloak-auth:keycloak-auth-domain"

	val imKeycloakF2CommonsApi = ":im-keycloak:keycloak-f2:commons:im-keycloak-f2-commons-api"
	val imKeycloakF2CommonsDomain = ":im-keycloak:keycloak-f2:commons:im-keycloak-f2-commons-domain"

	val imKeycloakF2ClientDomain = ":im-keycloak:keycloak-f2:client:im-keycloak-f2-client-domain"
	val imKeycloakF2ClientCommand = ":im-keycloak:keycloak-f2:client:im-keycloak-f2-client-command"
	val imKeycloakF2ClientQuery = ":im-keycloak:keycloak-f2:client:im-keycloak-f2-client-query"

	val imKeycloakF2GroupDomain = ":im-keycloak:keycloak-f2:group:im-keycloak-f2-group-domain"
	val imKeycloakF2GroupCommand = ":im-keycloak:keycloak-f2:group:im-keycloak-f2-group-command"
	val imKeycloakF2GroupQuery = ":im-keycloak:keycloak-f2:group:im-keycloak-f2-group-query"

	val imKeycloakF2RealmDomain = ":im-keycloak:keycloak-f2:realm:im-keycloak-f2-realm-domain"
	val imKeycloakF2RealmCommand = ":im-keycloak:keycloak-f2:realm:im-keycloak-f2-realm-command"
	val imKeycloakF2RealmQuery = ":im-keycloak:keycloak-f2:realm:im-keycloak-f2-realm-query"

	val imKeycloakF2RoleDomain = ":im-keycloak:keycloak-f2:role:im-keycloak-f2-role-domain"
	val imKeycloakF2RoleCommand = ":im-keycloak:keycloak-f2:role:im-keycloak-f2-role-command"
	val imKeycloakF2RoleQuery = ":im-keycloak:keycloak-f2:role:im-keycloak-f2-role-query"

	val imKeycloakF2UserDomain = ":im-keycloak:keycloak-f2:user:im-keycloak-f2-user-domain"
	val imKeycloakF2UserCommand = ":im-keycloak:keycloak-f2:user:im-keycloak-f2-user-command"
	val imKeycloakF2UserQuery = ":im-keycloak:keycloak-f2:user:im-keycloak-f2-user-query"

	val keycloakPlugin = ":im-keycloak:keycloak-plugin"
	val keycloakGenerateActionToken = ":im-keycloak:keycloak-plugin:keycloak-generate-action-token"
	val keycloakEventListenerHttp = ":im-keycloak:keycloak-plugin:keycloak-event-listener-http"
	val keycloakPluginClient = ":im-keycloak:keycloak-plugin:keycloak-plugin-client"
	val keycloakPluginDomain = ":im-keycloak:keycloak-plugin:keycloak-plugin-domain"

	val keycloakTestBdd = ":im-keycloak:keycloak-test:test-bdd"
	val keycloakTestIt = ":im-keycloak:keycloak-test:test-it"

}


