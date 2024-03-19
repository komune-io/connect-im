package io.komune.im.f2.privilege.domain.role.query

import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.f2.privilege.domain.role.model.RoleDTO
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a list of roles.
 * @d2 function
 * @parent [io.komune.im.f2.privilege.domain.D2RolePage]
 * @order 20
 */
typealias RoleListFunction = F2Function<RoleListQuery, RoleListResult>

/**
 * @d2 query
 * @parent [RoleListFunction]
 */
@JsExport
@JsName("RoleListQueryDTO")
interface RoleListQueryDTO {
    /**
     * Filter on applicable target. See [RoleTarget][io.komune.im.core.privilege.domain.model.RoleTarget]
     * @example "ORGANIZATION"
     */
    val target: String?
}

/**
 * @d2 inherit
 */
@Serializable
data class RoleListQuery(
    override val target: String?
): RoleListQueryDTO

/**
 * @d2 result
 * @parent [RoleListFunction]
 */
@JsExport
@JsName("RoleListResultDTO")
interface RoleListResultDTO {
    /**
     * Roles matching the filters.
     */
    val items: List<RoleDTO>
}

/**
 * @d2 inherit
 */
@Serializable
data class RoleListResult(
    override val items: List<Role>
): RoleListResultDTO
