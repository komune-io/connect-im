package city.smartb.im.f2.privilege.domain.role.query

import city.smartb.im.commons.model.ImQuery
import city.smartb.im.f2.privilege.domain.role.model.RoleDTO
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a list of roles.
 * @d2 function
 * @parent [city.smartb.im.f2.privilege.domain.D2RolePage]
 * @order 20
 */
typealias RoleListFunction = F2Function<RoleListQueryDTOBase, RoleListResultDTOBase>

/**
 * @d2 query
 * @parent [RoleListFunction]
 */
@JsExport
@JsName("RoleListQueryDTO")
interface RoleListQueryDTO: ImQuery {
    /**
     * Filter on applicable target. See [RoleTarget][city.smartb.im.core.privilege.domain.model.RoleTarget]
     * @example "ORGANIZATION"
     */
    val target: String?
}

/**
 * @d2 inherit
 */
@Serializable
data class RoleListQueryDTOBase(
    override val realmId: String? = null,
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
data class RoleListResultDTOBase(
    override val items: List<RoleDTOBase>
): RoleListResultDTO
