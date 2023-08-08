package i2.keycloak.f2.role.domain.features.query

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.RoleId
import i2.keycloak.f2.role.domain.RoleModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleGetByIdQueryFunction = F2Function<RoleGetByIdQuery, RoleGetByIdResult>

@JsExport
@JsName("RoleGetByIdQuery")
class RoleGetByIdQuery(
    val realmId: RealmId,
    val id: RoleId,
    val auth: AuthRealm,
)

@JsExport
@JsName("RoleGetByIdResult")
class RoleGetByIdResult(
    val item: RoleModel?
)
