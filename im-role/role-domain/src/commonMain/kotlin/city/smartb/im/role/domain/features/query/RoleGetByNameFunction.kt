package city.smartb.im.role.domain.features.query

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.RoleModel
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleGetByNameFunction = F2Function<RoleGetByNameQuery, RoleGetByNameResult>

@JsExport
@JsName("RoleGetByNameQueryDTO")
interface RoleGetByNameQueryDTO {
    val name: RoleName
}

@JsName("RoleGetByNameQuery")
class RoleGetByNameQuery(
    override val name: RoleName
): RoleGetByNameQueryDTO

@JsExport
@JsName("RoleGetByNameResultDTO")
interface RoleGetByNameResultDTO {
    val item: RoleModel?
}

@JsName("RoleGetByNameResult")
data class RoleGetByNameResult(
    override val item: RoleModel?
): RoleGetByNameResultDTO
