package i2.test.bdd.assertion

import city.smartb.im.infra.keycloak.RealmId
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.client.domain.ClientIdentifier
import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.ClientRepresentation
import javax.ws.rs.NotFoundException


fun AssertionKC.client(keycloak: Keycloak): AssertionClient = AssertionClient(keycloak)

class AssertionClient(
	private val keycloak: Keycloak
) {
	companion object

	fun exists(realmId: RealmId, id: ClientId) {
		try {
			val realm = keycloak.realm(realmId).clients().get(id)
			Assertions.assertThat(realm).isNotNull
		} catch (e: NotFoundException) {
			Assertions.fail("Client[${id} not found]", e)
		}
	}

	fun notExists(realmId: RealmId, id: ClientId) {
		try {
			keycloak.realm(realmId).clients().get(id).toRepresentation()
			Assertions.fail("Client[${id} exists]")
		} catch (e: NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}

	fun assertThat(realmId: RealmId, id: ClientId): ClientAssert {
		exists(realmId, id)
		val client = getClientRepresentation(realmId, id)
		return ClientAssert(client)
	}

	private fun getClientRepresentation(realmId: RealmId, id: ClientId): ClientRepresentation {
		return keycloak.realm(realmId).clients().get(id).toRepresentation()
	}

	inner class ClientAssert(
		private val client: ClientRepresentation
	) {
		fun hasFields(
            clientId: ClientId = client.id,
            clientIdentifier: ClientIdentifier = client.clientId,
            isPublicClient: Boolean = client.isPublicClient,
            isDirectAccessGrantsEnabled: Boolean = client.isDirectAccessGrantsEnabled,
            isServiceAccountsEnabled: Boolean = client.isServiceAccountsEnabled,
//			authorizationServicesEnabled: Boolean = client.authorizationServicesEnabled,
            rootUrl: String? = client.rootUrl,
            redirectUris: List<String> = client.redirectUris,
            baseUrl: String? = client.baseUrl,
            adminUrl: String = client.adminUrl,
            webOrigins: List<String> = client.webOrigins,
		) {
			Assertions.assertThat(client.id).isEqualTo(clientId)
			Assertions.assertThat(client.clientId).isEqualTo(clientIdentifier)
			Assertions.assertThat(client.isPublicClient).isEqualTo(isPublicClient)
			Assertions.assertThat(client.isDirectAccessGrantsEnabled).isEqualTo(isDirectAccessGrantsEnabled)
			Assertions.assertThat(client.isServiceAccountsEnabled).isEqualTo(isServiceAccountsEnabled)
//			Assertions.assertThat(client.authorizationServicesEnabled ?: false).isEqualTo(authorizationServicesEnabled)
			Assertions.assertThat(client.rootUrl).isEqualTo(rootUrl)
			Assertions.assertThat(client.redirectUris).isEqualTo(redirectUris)
			Assertions.assertThat(client.baseUrl).isEqualTo(baseUrl)
			Assertions.assertThat(client.adminUrl).isEqualTo(adminUrl)
			Assertions.assertThat(client.webOrigins).containsAll(webOrigins)
		}
	}
}
