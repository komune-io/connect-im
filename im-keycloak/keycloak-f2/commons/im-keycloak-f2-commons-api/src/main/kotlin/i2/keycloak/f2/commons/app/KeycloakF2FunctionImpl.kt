package i2.keycloak.f2.commons.app

import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.commons.utils.toJson
import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.client.KeycloakClientBuilder
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Message

fun <C: KeycloakF2Message, R: Any> keycloakF2Function(
    fcn: suspend (C, KeycloakClient) -> R
): F2Function<C, R> =
	f2Function { cmd ->
		try {
            // tmp fix
            val realmId = cmd.toJson().parseJsonTo<Map<String, Any>>()["realmId"] as String?

			val client = KeycloakClientBuilder.openConnection(cmd.auth).forRealm(realmId)
			fcn(cmd, client)
		} catch (e: Exception) {
			throw e.asI2Exception(e.message ?: "Internal Server Error")
		}
	}
