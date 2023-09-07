package i2.keycloak.f2.client.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.ClientId
import city.smartb.im.commons.model.RealmId
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientUpdateUrisFunction = F2Function<ClientUpdateUrisCommand, ClientUpdatedUrisEvent>

@JsExport
@JsName("ClientUpdateUrisCommand")
class ClientUpdateUrisCommand(
    val id: ClientId,
    val realmId: RealmId,
    override val auth: AuthRealm,
    val rootUrl: String,
    val redirectUris: List<String>,
    val baseUrl: String
): KeycloakF2Command

@JsExport
@JsName("ClientUpdatedUrisEvent")
class ClientUpdatedUrisEvent(
    val id: ClientId
)
