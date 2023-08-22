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

	// Come from gradle.properties
	fun keycloak() = System.getProperty("KEYCLOAK_VERSION").also { version ->
        println("Keycloak version: $version")
    }

	const val mockito = "4.6.1"
	const val testcontainers = "1.18.0"

	const val slf4j = FixersVersions.Logging.slf4j
}

object Dependencies {
	object Jvm {

		object Keycloak {
            fun adminClient(scope: Scope) = scope.add(
                "org.keycloak:keycloak-admin-client:${Versions.keycloak()}"
            )

            fun serverSpiPrivate(scope: Scope)  = scope.add(
            "org.keycloak:keycloak-server-spi-private:${Versions.keycloak()}",
            )

            fun all(scope: Scope)  = scope.add(
              "org.keycloak:keycloak-core:${Versions.keycloak()}",
              "org.keycloak:keycloak-server-spi:${Versions.keycloak()}",
              "org.keycloak:keycloak-server-spi-private:${Versions.keycloak()}",
              "org.keycloak:keycloak-services:${Versions.keycloak()}",
              "org.keycloak:keycloak-saml-core-public:${Versions.keycloak()}"
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
    object Infra {
        private const val BASE = ":im-infra"

        const val keycloak = "$BASE:keycloak"
        const val redis = "$BASE:infra-redis"
    }

	object S2 {
		const val imOrganizationApi = ":im-s2:im-organization:organization-api"
		const val imOrganizationDomain = ":im-s2:im-organization:organization-domain"
		const val imOrganizationClient = ":im-s2:im-organization:organization-client"
		const val imOrganizationLib = ":im-s2:im-organization:organization-lib"

		const val imUserApi = ":im-s2:im-user:user-api"
		const val imUserDomain = ":im-s2:im-user:user-domain"
		const val imUserClient = ":im-s2:im-user:user-client"
		const val imUserLib = ":im-s2:im-user:user-lib"

		const val imApikeyApi = ":im-s2:im-apikey:apikey-api"
		const val imApikeyDomain = ":im-s2:im-apikey:apikey-domain"
		const val imApikeyClient = ":im-s2:im-apikey:apikey-client"
		const val imApikeyLib = ":im-s2:im-apikey:apikey-lib"

		const val imRoleApi = ":im-s2:im-role:role-api"
		const val imRoleDomain = ":im-s2:im-role:role-domain"
		const val imRoleClient = ":im-s2:im-role:role-client"
	}

	object Script {
		const val imScriptGateway = ":im-script:im-script-gateway"
		const val imScriptConfig = ":im-script:im-script-gateway-configuration"

		const val imScriptFunctionConfig = ":im-script:im-script-function-config"
		const val imScriptFunctionCore = ":im-script:im-script-function-core"
		const val imScriptFunctionInit = ":im-script:im-script-function-init"
	}

    const val imApiConfig = ":im-api:api-config"

	const val imCommonsApi = ":im-commons:im-commons-api"
	const val imCommonsAuth = ":im-commons:im-commons-auth"
	const val imCommonsDomain = ":im-commons:im-commons-domain"

	const val imKeycloakF2CommonsApi = ":im-keycloak:keycloak-f2:commons:im-keycloak-f2-commons-api"
	const val imKeycloakF2CommonsDomain = ":im-keycloak:keycloak-f2:commons:im-keycloak-f2-commons-domain"

	const val imKeycloakF2ClientDomain = ":im-keycloak:keycloak-f2:client:im-keycloak-f2-client-domain"
	const val imKeycloakF2ClientCommand = ":im-keycloak:keycloak-f2:client:im-keycloak-f2-client-command"
	const val imKeycloakF2ClientQuery = ":im-keycloak:keycloak-f2:client:im-keycloak-f2-client-query"

	const val imKeycloakF2GroupDomain = ":im-keycloak:keycloak-f2:group:im-keycloak-f2-group-domain"
	const val imKeycloakF2GroupCommand = ":im-keycloak:keycloak-f2:group:im-keycloak-f2-group-command"
	const val imKeycloakF2GroupQuery = ":im-keycloak:keycloak-f2:group:im-keycloak-f2-group-query"

	const val imKeycloakF2RealmDomain = ":im-keycloak:keycloak-f2:realm:im-keycloak-f2-realm-domain"
	const val imKeycloakF2RealmCommand = ":im-keycloak:keycloak-f2:realm:im-keycloak-f2-realm-command"
	const val imKeycloakF2RealmQuery = ":im-keycloak:keycloak-f2:realm:im-keycloak-f2-realm-query"

	const val imKeycloakF2RoleDomain = ":im-keycloak:keycloak-f2:role:im-keycloak-f2-role-domain"
	const val imKeycloakF2RoleCommand = ":im-keycloak:keycloak-f2:role:im-keycloak-f2-role-command"
	const val imKeycloakF2RoleQuery = ":im-keycloak:keycloak-f2:role:im-keycloak-f2-role-query"

	const val imKeycloakF2UserDomain = ":im-keycloak:keycloak-f2:user:im-keycloak-f2-user-domain"
	const val imKeycloakF2UserCommand = ":im-keycloak:keycloak-f2:user:im-keycloak-f2-user-command"
	const val imKeycloakF2UserQuery = ":im-keycloak:keycloak-f2:user:im-keycloak-f2-user-query"

	const val keycloakPlugin = ":im-keycloak:keycloak-plugin"
	const val keycloakGenerateActionToken = ":im-keycloak:keycloak-plugin:keycloak-generate-action-token"
	const val keycloakEventListenerHttp = ":im-keycloak:keycloak-plugin:keycloak-event-listener-http"
	const val keycloakPluginClient = ":im-keycloak:keycloak-plugin:keycloak-plugin-client"
	const val keycloakPluginDomain = ":im-keycloak:keycloak-plugin:keycloak-plugin-domain"

	const val keycloakTestBdd = ":im-keycloak:keycloak-test:test-bdd"
	const val keycloakTestIt = ":im-keycloak:keycloak-test:test-it"
}
