package i2.keycloak.f2.user.domain.features.command

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.user.domain.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserEmailSendActionsFunction = F2Function<UserEmailSendActionsCommand, UserEmailSentActionsEvent>

@JsExport
@JsName("UserEmailSendActionsCommand")
class UserEmailSendActionsCommand(
    val userId: UserId,
    val clientId: String?,
    val redirectUri: String?,
    val actions: List<String>,
    val realmId: RealmId,
    override val auth: AuthRealm
): KeycloakF2Command

@JsExport
@JsName("UserEmailSentActionsEvent")
class UserEmailSentActionsEvent(
    val id: UserId
): KeycloakF2Result
