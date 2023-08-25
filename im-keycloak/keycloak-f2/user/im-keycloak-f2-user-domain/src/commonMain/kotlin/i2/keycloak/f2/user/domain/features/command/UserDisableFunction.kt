package i2.keycloak.f2.user.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.user.domain.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserDisableFunction = F2Function<UserDisableCommand, UserDisabledEvent>

@JsExport
@JsName("UserDisableCommand")
class UserDisableCommand(
    val id: UserId,
    val realmId: RealmId,
    override val auth: AuthRealm,
) : KeycloakF2Command

@JsExport
@JsName("UserDisabledEvent")
class UserDisabledEvent(
	val id: UserId
) : Event
