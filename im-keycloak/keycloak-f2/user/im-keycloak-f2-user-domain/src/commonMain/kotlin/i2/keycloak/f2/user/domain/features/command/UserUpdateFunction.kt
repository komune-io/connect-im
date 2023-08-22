package i2.keycloak.f2.user.domain.features.command

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.user.domain.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserUpdateFunction = F2Function<UserUpdateCommand, UserUpdatedEvent>

@JsExport
@JsName("UserUpdateCommand")
class UserUpdateCommand(
    val userId: UserId,
    val realmId: RealmId,
    val auth: AuthRealm,
    val firstname: String?,
    val lastname: String?,
    val attributes: Map<String, String>,
): Command

@JsExport
@JsName("UserUpdatedEvent")
class UserUpdatedEvent(
	val id: UserId
): Event
