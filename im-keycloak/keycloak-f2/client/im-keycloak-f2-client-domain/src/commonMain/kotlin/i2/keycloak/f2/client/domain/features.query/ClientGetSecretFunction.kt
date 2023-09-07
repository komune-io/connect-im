package i2.keycloak.f2.client.domain.features.query

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.ClientId
import city.smartb.im.commons.model.RealmId
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Query
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetSecretFunction = F2Function<ClientGetSecretQuery, ClientGetSecretResult>

@JsExport
@JsName("ClientGetSecretQuery")
class ClientGetSecretQuery(
    val clientId: ClientId,
    val realmId: RealmId,
    override val auth: AuthRealm
): KeycloakF2Query

@JsExport
@JsName("ClientGetSecretResult")
class ClientGetSecretResult(
    val secret: String?
)
