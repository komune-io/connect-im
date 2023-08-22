package i2.keycloak.f2.user.domain.features.query

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Query
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.f2.user.domain.model.UserModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetFunction = F2Function<UserGetQuery, UserGetResult>

@JsExport
@JsName("UserGetQuery")
class UserGetQuery(
    val id: UserId,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Query

@JsExport
@JsName("UserGetResult")
class UserGetResult(
	val item: UserModel?
): Event
