package city.smartb.im.f2.privilege.domain.permission.query

import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTO
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a list of permissions.
 * @d2 function
 * @parent [city.smartb.im.f2.privilege.domain.D2PermissionPage]
 * @order 20
 */
typealias PermissionListFunction = F2Function<PermissionListQueryDTOBase, PermissionListResultDTOBase>

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
class PermissionListQueryDTOBase: PermissionListQueryDTO

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
data class PermissionListResultDTOBase(
    override val items: List<PermissionDTOBase>
): PermissionListResultDTO
