package i2.keycloak.f2.commons.app

import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.client.KeycloakClientBuilder
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Message
import i2.keycloak.f2.commons.domain.error.I2Exception

fun <C: KeycloakF2Message, R: Any> keycloakF2Function(
    fcn: suspend (C, KeycloakClient) -> R
): F2Function<C, R> =
	f2Function { cmd ->
		try {
            val client = KeycloakClientBuilder.openConnection(cmd.auth).forRealm(cmd.auth.space)
            fcn(cmd, client)
        } catch (e: I2Exception) {
            throw e.from ?: e
		} catch (e: Exception) {
			throw e.asI2Exception(e.message ?: "Internal Server Error")
		}
	}
