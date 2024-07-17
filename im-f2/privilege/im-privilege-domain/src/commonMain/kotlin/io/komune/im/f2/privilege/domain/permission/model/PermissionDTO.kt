package io.komune.im.f2.privilege.domain.permission.model

import io.komune.im.commons.model.FeatureIdentifier
import io.komune.im.commons.model.PermissionId
import io.komune.im.commons.model.PermissionIdentifier
import io.komune.im.core.privilege.domain.model.PrivilegeType
import io.komune.im.f2.privilege.domain.model.PrivilegeDTO
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Allowed action within a system.
 * @d2 model
 * @parent [io.komune.im.f2.privilege.domain.D2PermissionPage]
 * @order 10
 */
@JsExport
interface PermissionDTO: PrivilegeDTO {
    /**
     * Generated id of the permission.
     */
    override val id: PermissionId

    /**
     * @ref [PrivilegeDTO.type]
     * @example "PERMISSION"
     */
    override val type: String

    /**
     * Identifier of the permission. Must be unique within a realm.
     * @example "im_organization_write"
     */
    override val identifier: PermissionIdentifier

    /**
     * Description of the permission.
     * @example "Ability to modify organization data"
     */
    override val description: String

    /**
     * List of features needed to enable this permission. The first level is a logical OR, the second level is a logical AND.
     * If the list is null or empty, the permission is always enabled.
     * @example [["feat_organization"]]
     */
    val features: List<List<FeatureIdentifier>>?
}

/**
 * @d2 inherit
 */
@Serializable
data class Permission(
    override val id: PermissionId,
    override val identifier: PermissionIdentifier,
    override val description: String,
    override val features: List<List<FeatureIdentifier>>?
): PermissionDTO {
    override val type = PrivilegeType.PERMISSION.name
}
