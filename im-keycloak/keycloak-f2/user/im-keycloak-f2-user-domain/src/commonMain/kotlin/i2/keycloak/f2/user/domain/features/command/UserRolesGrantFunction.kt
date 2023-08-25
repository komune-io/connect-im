package i2.keycloak.f2.user.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.user.domain.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserRolesGrantFunction = F2Function<UserRolesGrantCommand, UserRolesGrantedEvent>

@JsExport
@JsName("UserRolesGrantCommand")
class UserRolesGrantCommand(
    val id: UserId,
    val roles: List<String>,
    override val auth: AuthRealm,
    val realmId: RealmId = auth.realmId,
    val clientId: String? = null
): KeycloakF2Command

@JsExport
@JsName("UserRolesGrantedEvent")
class UserRolesGrantedEvent(
	val id: String
): Event
