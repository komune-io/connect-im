package i2.test.bdd.data.client

import city.smartb.im.infra.keycloak.AuthRealm
import city.smartb.im.infra.keycloak.RealmId
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.client.domain.features.command.ClientCreateCommand
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.clientCreateCommand(
    realmId: RealmId,
    auth: AuthRealm,
    clientIdentifier: ClientIdentifier = UUID.randomUUID().toString(),
    isPublicClient: Boolean = true,
    isDirectAccessGrantsEnabled: Boolean = true,
    rootUrl: String? = null,
    redirectUris: List<String> = emptyList(),
    baseUrl: String = "",
    adminUrl: String = "",
    webOrigins: List<String> = emptyList(),
    protocolMappers: Map<String, String> = emptyMap(),
) = ClientCreateCommand(
    clientIdentifier = clientIdentifier,
    realmId = realmId,
    isPublicClient = isPublicClient,
    isDirectAccessGrantsEnabled = isDirectAccessGrantsEnabled,
    rootUrl = rootUrl,
    redirectUris = redirectUris,
    baseUrl = baseUrl,
    adminUrl = adminUrl,
    webOrigins = webOrigins,
    protocolMappers = protocolMappers,
    auth = auth
)
