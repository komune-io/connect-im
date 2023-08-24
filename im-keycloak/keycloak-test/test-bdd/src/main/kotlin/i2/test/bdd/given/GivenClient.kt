package i2.test.bdd.given

import city.smartb.im.commons.model.RealmId
import city.smartb.im.infra.keycloak.client.KeycloakClient
import f2.dsl.fnc.invoke
import i2.keycloak.f2.client.command.ClientCreateFunctionImpl
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.client.clientCreateCommand
import kotlinx.coroutines.runBlocking
import java.util.UUID

class GivenClient(
    private val client: KeycloakClient,
) {

	fun withClient(
        realmId: RealmId,
        clientIdentifier: ClientIdentifier = UUID.randomUUID().toString(),
    ) = runBlocking {
		val cmd = DataTest.clientCreateCommand(
			realmId = realmId,
			clientIdentifier = clientIdentifier,
			auth = client.auth,
			isPublicClient = false
		)
		ClientCreateFunctionImpl().clientCreateFunction().invoke(cmd).id
	}
}

fun GivenKC.client() = GivenClient(client)
