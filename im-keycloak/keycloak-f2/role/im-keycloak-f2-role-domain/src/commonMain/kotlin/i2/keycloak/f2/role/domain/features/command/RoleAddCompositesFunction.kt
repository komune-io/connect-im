package i2.keycloak.f2.role.domain.features.command

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.role.domain.RoleName
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleAddCompositesFunction = F2Function<RoleAddCompositesCommand, RoleAddedCompositesEvent>

@JsExport
@JsName("RoleAddCompositesCommand")
class RoleAddCompositesCommand(
    val roleName: RoleName,
    val composites: List<RoleName>,
    override val auth: AuthRealm,
    val realmId: RealmId
): KeycloakF2Command

@JsExport
@JsName("RoleAddedCompositesEvent")
class RoleAddedCompositesEvent(
	val id: String
): KeycloakF2Result
