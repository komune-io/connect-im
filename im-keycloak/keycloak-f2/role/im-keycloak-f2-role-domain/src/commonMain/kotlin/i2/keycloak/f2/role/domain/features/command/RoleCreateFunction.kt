package i2.keycloak.f2.role.domain.features.command

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.role.domain.RoleId
import i2.keycloak.f2.role.domain.RoleName
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleCreateFunction = F2Function<RoleCreateCommand, RoleCreatedEvent>

@JsExport
@JsName("RoleCreateCommand")
class RoleCreateCommand(
    val name: RoleName,
    val description: String?,
    val isClientRole: Boolean,
    val composites: List<RoleName>,
    override val auth: AuthRealm,
    val realmId: RealmId
): KeycloakF2Command

@JsExport
@JsName("RoleCreatedEvent")
class RoleCreatedEvent(
	val id: RoleId
): KeycloakF2Result
