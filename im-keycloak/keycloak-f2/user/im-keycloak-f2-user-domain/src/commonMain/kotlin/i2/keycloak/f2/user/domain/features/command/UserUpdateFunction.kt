package i2.keycloak.f2.user.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import city.smartb.im.commons.model.UserId
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserUpdateFunction = F2Function<UserUpdateCommand, UserUpdatedEvent>

@JsExport
@JsName("UserUpdateCommand")
class UserUpdateCommand(
    val userId: UserId,
    val realmId: RealmId,
    override val auth: AuthRealm,
    val firstname: String?,
    val lastname: String?,
    val attributes: Map<String, String>,
): KeycloakF2Command

@JsExport
@JsName("UserUpdatedEvent")
class UserUpdatedEvent(
	val id: UserId
): Event
