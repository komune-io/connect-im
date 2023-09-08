package city.smartb.im.f2.privilege.domain.permission.query

import city.smartb.im.commons.model.PermissionIdentifier
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTO
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a permission by identifier.
 * @d2 function
 * @parent [city.smartb.im.f2.privilege.domain.D2PermissionPage]
 * @order 10
 */
typealias PermissionGetFunction = F2Function<PermissionGetQueryDTOBase, PermissionGetResultDTOBase>

/**
 * @d2 query
 * @parent [PermissionGetFunction]
 */
@JsExport
@JsName("PermissionGetQueryDTO")
interface PermissionGetQueryDTO {
    /**
     * Identifier of the permission to get.
     * @example [city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase.identifier]
     */
    val identifier: PermissionIdentifier
}

@Serializable
data class PermissionGetQueryDTOBase(
    override val identifier: PermissionIdentifier
): PermissionGetQueryDTO

/**
 * @d2 result
 * @parent [PermissionGetFunction]
 */
@JsExport
@JsName("PermissionGetResultDTO")
interface PermissionGetResultDTO {
    /**
     * Permission matching the given identifier, or null if not found
     */
    val item: PermissionDTO?
}

/**
 * @d2 inherit
 */
@Serializable
data class PermissionGetResultDTOBase(
    override val item: PermissionDTOBase?
): PermissionGetResultDTO
