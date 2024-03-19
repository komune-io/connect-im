import io.komune.gradle.dependencies.FixersDependencies
import io.komune.gradle.dependencies.FixersPluginVersions
import io.komune.gradle.dependencies.FixersVersions
import io.komune.gradle.dependencies.Scope
import io.komune.gradle.dependencies.add

object Framework {
	val fixers = FixersPluginVersions.fixers
	val connect = "0.15.0-SNAPSHOT"
}

object PluginVersions {
	val fixers = Framework.fixers
	const val springBoot = FixersPluginVersions.springBoot
	const val kotlin = FixersPluginVersions.kotlin

	const val shadowJar = "7.1.2"
}

object Versions {
	val f2 = Framework.fixers
	val s2 = Framework.fixers

	val fs = Framework.connect

	const val ktor = FixersVersions.Kotlin.ktor

	const val springBoot = PluginVersions.springBoot
	const val springData = FixersVersions.Spring.data
	const val springSecurity = "6.1.3"

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
			"io.komune.f2:f2-spring-boot-starter-function-http:${Versions.f2}"
		)

		fun f2Function(scope: Scope) = scope.add(
			"io.komune.f2:f2-spring-boot-starter-function:${Versions.f2}"
		)

		fun slf4j(scope: Scope) = scope.add(
			"org.slf4j:slf4j-api:${Versions.slf4j}",
		)

		object Fs {
			fun client(scope: Scope) = scope.add(
				"io.komune.fs:fs-file-client:${Versions.fs}"
			)
		}

		fun f2Auth(scope: Scope) = scope.add(
//			"io.komune.i2:i2-spring-boot-starter-auth:${Versions.i2}"
		)

		fun i2AuthClient(scope: Scope) = scope.add(
//			"io.komune.i2:keycloak-auth-client:${Versions.i2}"
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
			"io.komune.f2:f2-dsl-cqrs:${Versions.f2}",
			"io.komune.f2:f2-dsl-function:${Versions.f2}"
		)

        fun f2Client(scope: Scope) = scope.add(
            "io.komune.f2:f2-client-ktor:${Versions.f2}",
            "io.ktor:ktor-client-auth:${Versions.ktor}"
        )

		fun datetime(scope: Scope) = scope.add(
			"org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kdatetime}"
		)

		fun ktor(scope: Scope) = scope.add(
			"io.ktor:ktor-client-core:${Versions.ktor}",
			"io.ktor:ktor-client-serialization:${Versions.ktor}"
		)
		fun s2(scope: Scope) = scope.add(
			"io.komune.s2:s2-automate-dsl:${Versions.s2}"
		)
	}
}

object Modules {
    object Api {
        private const val BASE = ":im-api:api"
        const val config = "$BASE-config"
    }

    object Commons {
        private const val BASE = ":im-commons:im-commons"
        const val api = "$BASE-api"
        const val auth = "$BASE-auth"
        const val domain = "$BASE-domain"
    }

    object Core {
        private const val BASE = ":im-core"

        const val commons = "$BASE:im-commons-core"

        private const val CLIENT = "$BASE:client-core:im-client-core"
        const val clientApi = "$CLIENT-api"
        const val clientDomain = "$CLIENT-domain"

        private const val ORGANIZATION = "$BASE:organization-core:im-organization-core"
        const val organizationApi = "$ORGANIZATION-api"
        const val organizationDomain = "$ORGANIZATION-domain"

        private const val PRIVILEGE = "$BASE:privilege-core:im-privilege-core"
        const val privilegeApi = "$PRIVILEGE-api"
        const val privilegeDomain = "$PRIVILEGE-domain"

        private const val USER = "$BASE:user-core:im-user-core"
        const val userApi = "$USER-api"
        const val userDomain = "$USER-domain"
    }

	object F2 {
        private const val BASE = ":im-f2"

        private const val API_KEY = "$BASE:apikey:im-apikey"
        const val apikeyApi = "$API_KEY-api"
        const val apikeyClient = "$API_KEY-client"
        const val apikeyDomain = "$API_KEY-domain"
        const val apikeyLib = "$API_KEY-lib"

        private const val ORGANIZATION = "$BASE:organization:im-organization"
		const val organizationApi = "$ORGANIZATION-api"
        const val organizationClient = "$ORGANIZATION-client"
        const val organizationDomain = "$ORGANIZATION-domain"
		const val organizationLib = "$ORGANIZATION-lib"

        private const val PRIVILEGE = "$BASE:privilege:im-privilege"
		const val privilegeApi = "$PRIVILEGE-api"
        const val privilegeClient = "$PRIVILEGE-client"
        const val privilegeDomain = "$PRIVILEGE-domain"
        const val privilegeLib = "$PRIVILEGE-lib"

        private const val SPACE = "$BASE:space:im-space"
        val spaceApi = "$SPACE-api"
        val spaceDomain = "$SPACE-domain"
        val spaceClient = "$SPACE-client"
        val spaceLib = "$SPACE-lib"

        private const val USER = "$BASE:user:im-user"
        const val userApi = "$USER-api"
        const val userClient = "$USER-client"
        const val userDomain = "$USER-domain"
        const val userLib = "$USER-lib"
	}

    object Keycloak {
        private const val BASE = ":im-keycloak:keycloak-plugin"
        const val generateActionToken = "$BASE:im-keycloak-generate-action-token"
        const val eventListenerHttp = "$BASE:im-keycloak-event-listener-http"
        const val pluginClient = "$BASE:im-keycloak-plugin-client"
        const val pluginDomain = "$BASE:im-keycloak-plugin-domain"
    }

    object Infra {
        private const val BASE = ":im-infra"
        const val keycloak = "$BASE:im-keycloak"
        const val redis = "$BASE:im-redis"
    }

	object Script {
        const val BASE = ":im-script:im-script"
        const val core = "$BASE-core"
        const val gateway = "$BASE-gateway"
        const val init = "$BASE-init"
        const val spaceConfig = "$BASE-space-config"
		const val spaceCreate = "$BASE-space-create"
	}
}
