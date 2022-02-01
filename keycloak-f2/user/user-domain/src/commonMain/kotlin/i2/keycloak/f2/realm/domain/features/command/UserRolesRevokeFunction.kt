package i2.keycloak.f2.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.realm.domain.UserId
import i2.keycloak.master.domain.AuthRealm
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserRolesRevokeFunction = F2Function<UserRolesRevokeCommand, UserRolesRevokedResult>

@JsExport
@JsName("UserRolesRevokeCommand")
class UserRolesRevokeCommand(
	val id: UserId,
	val roles: List<String>,
	val auth: AuthRealm,
) : Command

@JsExport
@JsName("UserRolesRevokedResult")
class UserRolesRevokedResult(
	val id: String
) : Event
