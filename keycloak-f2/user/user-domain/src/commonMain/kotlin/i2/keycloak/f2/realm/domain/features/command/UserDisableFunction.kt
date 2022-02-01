package i2.keycloak.f2.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.realm.domain.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserDisableFunction = F2Function<UserDisableCommand, UserDisabledResult>

@JsExport
@JsName("UserDisableCommand")
class UserDisableCommand(
	val id: UserId,
	val realmId: RealmId,
	val auth: AuthRealm,
) : Command

@JsExport
@JsName("UserDisabledResult")
class UserDisabledResult(
	val id: UserId
) : Event
