package i2.keycloak.f2.user.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.user.domain.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserSetAttributesFunction = F2Function<UserSetAttributesCommand, UserSetAttributesEvent>

@JsExport
@JsName("UserSetAttributesCommand")
class UserSetAttributesCommand(
    val id: UserId,
    val attributes: Map<String, String?>,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("UserSetAttributesEvent")
class UserSetAttributesEvent(
	val id: UserId
): Event
