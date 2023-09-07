package i2.keycloak.f2.user.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import city.smartb.im.commons.model.UserId
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserRolesRevokeFunction = F2Function<UserRolesRevokeCommand, UserRolesRevokedEvent>

@JsExport
@JsName("UserRolesRevokeCommand")
class UserRolesRevokeCommand(
    val id: UserId,
    val roles: List<String>,
    override val auth: AuthRealm,
    val realmId: RealmId = auth.space
): KeycloakF2Command

@JsExport
@JsName("UserRolesRevokedEvent")
class UserRolesRevokedEvent(
	val id: String
): Event
