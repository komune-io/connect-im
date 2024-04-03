package io.komune.im.f2.privilege.domain.permission.query

import io.komune.im.commons.model.PermissionIdentifier
import io.komune.im.f2.privilege.domain.permission.model.Permission
import io.komune.im.f2.privilege.domain.permission.model.PermissionDTO
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a permission by identifier.
 * @d2 function
 * @parent [io.komune.im.f2.privilege.domain.D2PermissionPage]
 * @order 10
 */
typealias PermissionGetFunction = F2Function<PermissionGetQuery, PermissionGetResult>

/**
 * @d2 query
 * @parent [PermissionGetFunction]
 */
@JsExport
@JsName("PermissionGetQueryDTO")
interface PermissionGetQueryDTO {
    /**
     * Identifier of the permission to get.
     * @example [io.komune.im.f2.privilege.domain.permission.model.Permission.identifier]
     */
    val identifier: PermissionIdentifier
}

@Serializable
data class PermissionGetQuery(
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
