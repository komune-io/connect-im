package city.smartb.im.f2.privilege.domain.role.query

import city.smartb.im.commons.model.ImQuery
import city.smartb.im.f2.privilege.domain.role.model.RoleDTO
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase
import city.smartb.im.f2.privilege.domain.role.model.RoleIdentifier
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a role by identifier.
 * @d2 function
 * @parent [city.smartb.im.f2.privilege.domain.D2RolePage]
 * @order 10
 */
typealias RoleGetFunction = F2Function<RoleGetQueryDTOBase, RoleGetResultDTOBase>

/**
 * @d2 query
 * @parent [RoleGetFunction]
 */
@JsExport
@JsName("RoleGetQueryDTO")
interface RoleGetQueryDTO: ImQuery {
    /**
     * Identifier of the role to get.
     * @example [city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase.identifier]
     */
    val identifier: RoleIdentifier
}

@Serializable
class RoleGetQueryDTOBase(
    override val realmId: String? = null,
    override val identifier: RoleIdentifier
): RoleGetQueryDTO

/**
 * @d2 result
 * @parent [RoleGetFunction]
 */
@JsExport
@JsName("RoleGetResultDTO")
interface RoleGetResultDTO {
    /**
     * Role matching the given identifier, or null if not found
     */
    val item: RoleDTO?
}

/**
 * @d2 inherit
 */
@Serializable
data class RoleGetResultDTOBase(
    override val item: RoleDTOBase?
): RoleGetResultDTO
