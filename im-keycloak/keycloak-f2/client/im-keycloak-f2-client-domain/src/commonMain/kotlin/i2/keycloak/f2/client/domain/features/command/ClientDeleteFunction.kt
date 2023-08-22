package i2.keycloak.f2.client.domain.features.command

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientDeleteFunction = F2Function<ClientDeleteCommand, ClientDeletedEvent>

@JsExport
@JsName("ClientDeleteCommand")
class ClientDeleteCommand(
    val id: ClientId,
    val realmId: RealmId,
    val auth: AuthRealm
)

@JsExport
@JsName("ClientDeletedEvent")
class ClientDeletedEvent(
    val id: ClientId,
)
