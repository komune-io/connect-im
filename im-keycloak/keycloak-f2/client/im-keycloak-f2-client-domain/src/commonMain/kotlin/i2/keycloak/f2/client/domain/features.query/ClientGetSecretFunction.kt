package i2.keycloak.f2.client.domain.features.query

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetSecretFunction = F2Function<ClientGetSecretQuery, ClientGetSecretResult>

@JsExport
@JsName("ClientGetSecretQuery")
class ClientGetSecretQuery(
    val clientId: ClientId,
    val realmId: RealmId,
    val auth: AuthRealm
)

@JsExport
@JsName("ClientGetSecretResult")
class ClientGetSecretResult(
    val secret: String?
)
