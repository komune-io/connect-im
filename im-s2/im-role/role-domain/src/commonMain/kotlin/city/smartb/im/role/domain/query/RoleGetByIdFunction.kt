package city.smartb.im.role.domain.query

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.RoleId
import i2.keycloak.f2.role.domain.RoleModel
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RoleGetByIdFunction = F2Function<RoleGetByIdQuery, RoleGetByIdResult>

@JsExport
interface RoleGetByIdQueryDTO {
    val id: RoleId
}

@JsName("RoleGetByIdQuery")
class RoleGetByIdQuery(
    override val id: RoleId,
) : RoleGetByIdQueryDTO


@JsExport
@JsName("RoleGetByIdResultDTO")
interface RoleGetByIdResultDTO {
    val item: RoleModel?
}

@JsName("RoleGetByIdResult")
class RoleGetByIdResult(
    override val item: RoleModel?,
): RoleGetByIdResultDTO
