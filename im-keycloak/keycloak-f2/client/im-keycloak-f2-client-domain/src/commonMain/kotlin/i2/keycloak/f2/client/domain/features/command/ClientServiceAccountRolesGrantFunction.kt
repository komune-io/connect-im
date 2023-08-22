package i2.keycloak.f2.client.domain.features.command

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientServiceAccountRolesGrantFunction = F2Function<ClientServiceAccountRolesGrantCommand, ClientServiceAccountRolesGrantedEvent>

@JsExport
@JsName("ClientServiceAccountRolesGrantCommand")
class ClientServiceAccountRolesGrantCommand(
    val id: ClientId,
    val roles: List<String>,
    val auth: AuthRealm,
    val realmId: RealmId = auth.realmId
): Command

@JsExport
@JsName("ClientServiceAccountRolesGrantedEvent")
class ClientServiceAccountRolesGrantedEvent(
    val id: String
): Event
