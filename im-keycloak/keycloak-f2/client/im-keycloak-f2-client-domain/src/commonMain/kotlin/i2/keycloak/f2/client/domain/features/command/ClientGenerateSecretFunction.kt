package i2.keycloak.f2.client.domain.features.command

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGenerateSecretFunction = F2Function<ClientGenerateSecretCommand, ClientGeneratedSecretEvent>

@JsExport
@JsName("ClientGenerateSecretCommand")
class ClientGenerateSecretCommand(
    val id: ClientId,
    val realmId: RealmId,
    val auth: AuthRealm
)

@JsExport
@JsName("ClientGeneratedSecretEvent")
class ClientGeneratedSecretEvent(
    val secret: String
)
