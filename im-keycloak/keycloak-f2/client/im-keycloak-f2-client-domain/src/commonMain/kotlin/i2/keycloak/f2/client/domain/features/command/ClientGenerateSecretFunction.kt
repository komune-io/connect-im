package i2.keycloak.f2.client.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.ClientId
import city.smartb.im.commons.model.RealmId
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGenerateSecretFunction = F2Function<ClientGenerateSecretCommand, ClientGeneratedSecretEvent>

@JsExport
@JsName("ClientGenerateSecretCommand")
class ClientGenerateSecretCommand(
    val id: ClientId,
    val realmId: RealmId,
    override val auth: AuthRealm
): KeycloakF2Command

@JsExport
@JsName("ClientGeneratedSecretEvent")
class ClientGeneratedSecretEvent(
    val secret: String
)
