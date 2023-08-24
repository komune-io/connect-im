package i2.keycloak.f2.user.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.user.domain.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserCreateFunction = F2Function<UserCreateCommand, UserCreatedCommand>

@JsExport
@JsName("UserCreateCommand")
class UserCreateCommand(
    val realmId: RealmId,
    val username: String,
    val firstname: String?,
    val lastname: String?,
    val email: String,
    val isEnable: Boolean,
    val isEmailVerified: Boolean,
    val attributes: Map<String, String>,
    override val auth: AuthRealm,
    val password: String? = null,
    val isPasswordTemporary: Boolean = false
) : KeycloakF2Command

@JsExport
@JsName("UserCreatedEvent")
class UserCreatedCommand(
	val id: UserId
) : Event
