package io.komune.im.f2.privilege.domain.role.query

import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.f2.privilege.domain.role.model.RoleDTO
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Get a role by identifier.
 * @d2 function
 * @parent [io.komune.im.f2.privilege.domain.D2RolePage]
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
     * @example [io.komune.im.f2.privilege.domain.role.model.Role.identifier]
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
