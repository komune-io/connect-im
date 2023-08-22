package i2.keycloak.f2.role.domain.features.query

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.RoleModel
import i2.keycloak.f2.role.domain.RoleName
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleGetByNameQueryFunction = F2Function<RoleGetByNameQuery, RoleGetByNameResult>

@JsExport
@JsName("RoleGetByNameQuery")
class RoleGetByNameQuery(
    val realmId: RealmId,
    val auth: AuthRealm,
    val name: RoleName
)

@JsExport
@JsName("RoleGetByNameResult")
class RoleGetByNameResult(
    val item: RoleModel?
)
