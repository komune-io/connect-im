package city.smartb.im.privilege.domain.role.query

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.role.domain.Role
import i2.keycloak.f2.role.domain.RoleId
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
    val item: Role?
}

@JsName("RoleGetByIdResult")
class RoleGetByIdResult(
    override val item: Role?,
): RoleGetByIdResultDTO
