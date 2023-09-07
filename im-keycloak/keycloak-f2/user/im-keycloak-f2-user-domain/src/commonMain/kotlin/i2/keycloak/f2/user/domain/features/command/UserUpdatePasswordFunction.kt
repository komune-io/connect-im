package i2.keycloak.f2.user.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import city.smartb.im.commons.model.UserId
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserUpdatePasswordFunction = F2Function<UserUpdatePasswordCommand, UserUpdatedPasswordEvent>

@JsExport
@JsName("UserUpdatePasswordCommand")
class UserUpdatePasswordCommand(
    val userId: UserId,
    val password: String,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("UserUpdatedPasswordEvent")
class UserUpdatedPasswordEvent(
	val userId: UserId
): KeycloakF2Result
