package i2.test.bdd.data.client

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.client.domain.features.command.ClientUpdateUrisCommand
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.clientUpdateUrisCommand(
    realmId: RealmId,
    auth: AuthRealm,
    id: ClientId = UUID.randomUUID().toString(),
    rootUrl: String = "",
    redirectUris: List<String> = emptyList(),
    baseUrl: String = ""
) = ClientUpdateUrisCommand(
    realmId = realmId,
    auth = auth,
    id = id,
    rootUrl = rootUrl,
    redirectUris = redirectUris,
    baseUrl = baseUrl
)
