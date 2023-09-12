package city.smartb.im.f2.privilege.domain.role.query

import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.f2.privilege.domain.role.model.Role
import city.smartb.im.f2.privilege.domain.role.model.RoleDTO
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
typealias RoleGetFunction = F2Function<RoleGetQuery, RoleGetResult>

/**
 * @d2 query
 * @parent [RoleGetFunction]
 */
@JsExport
@JsName("RoleGetQueryDTO")
interface RoleGetQueryDTO {
    /**
     * Identifier of the role to get.
     * @example [city.smartb.im.f2.privilege.domain.role.model.Role.identifier]
     */
    val identifier: RoleIdentifier
}

/**
 * @d2 inherit
 */
@Serializable
data class RoleGetQuery(
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
data class RoleGetResult(
    override val item: Role?
): RoleGetResultDTO
