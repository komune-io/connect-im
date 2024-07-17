package io.komune.im.f2.privilege.domain.permission.query

import f2.dsl.fnc.F2Function
import io.komune.im.f2.privilege.domain.permission.model.Permission
import io.komune.im.f2.privilege.domain.permission.model.PermissionDTO
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a list of permissions.
 * @d2 function
 * @parent [io.komune.im.f2.privilege.domain.D2PermissionPage]
 * @order 20
 */
typealias PermissionListFunction = F2Function<PermissionListQuery, PermissionListResult>

/**
 * @d2 query
 * @parent [PermissionListFunction]
 */
@JsExport
@JsName("PermissionListQueryDTO")
interface PermissionListQueryDTO

/**
 * @d2 inherit
 */
@Serializable
class PermissionListQuery: PermissionListQueryDTO

/**
 * @d2 result
 * @parent [PermissionListFunction]
 */
@JsExport
@JsName("PermissionListResultDTO")
interface PermissionListResultDTO {
    /**
     * Permissions matching the filters.
     */
    val items: List<PermissionDTO>
}

/**
 * @d2 inherit
 */
@Serializable
data class PermissionListResult(
    override val items: List<Permission>
): PermissionListResultDTO
