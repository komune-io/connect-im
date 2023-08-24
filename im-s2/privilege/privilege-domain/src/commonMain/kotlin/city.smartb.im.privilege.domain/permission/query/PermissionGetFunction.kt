package city.smartb.im.privilege.domain.permission.query

import city.smartb.im.commons.model.ImQuery
import city.smartb.im.privilege.domain.permission.model.Permission
import city.smartb.im.privilege.domain.permission.model.PermissionDTO
import city.smartb.im.privilege.domain.permission.model.PermissionIdentifier
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a permission by identifier.
 * @d2 function
 * @parent [city.smartb.im.privilege.domain.D2PermissionPage]
 * @order 10
 */
typealias PermissionGetFunction = F2Function<PermissionGetQuery, PermissionGetResult>

/**
 * @d2 query
 * @parent [PermissionGetFunction]
 */
@JsExport
@JsName("PermissionGetQueryDTO")
interface PermissionGetQueryDTO: ImQuery {
    /**
     * Identifier of the permission to get.
     * @example [city.smartb.im.privilege.domain.permission.model.Permission.identifier]
     */
    val identifier: PermissionIdentifier
}

@Serializable
class PermissionGetQuery(
    override val realmId: String? = null,
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
data class PermissionGetResult(
    override val item: Permission?
): PermissionGetResultDTO
