package city.smartb.im.privilege.domain.permission.model

import city.smartb.im.privilege.domain.model.Privilege
import city.smartb.im.privilege.domain.model.PrivilegeIdentifier
import city.smartb.im.privilege.domain.model.PrivilegeType
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * @d2 hidden
 * @visual json "868044f8-1852-43eb-897d-067bc4631396"
 */
typealias PermissionId = String

/**
 * @d2 hidden
 */
typealias PermissionIdentifier = PrivilegeIdentifier

/**
 * Allowed action within a system.
 * @d2 model
 * @parent [city.smartb.im.privilege.domain.D2PermissionPage]
 * @order 10
 */
@JsExport
interface PermissionDTO {
    /**
     * Generated id of the permission.
     */
    val id: PermissionId

    /**
     * Identifier of the permission. Must be unique within a realm.
     * @example "im_organization_write"
     */
    val identifier: PermissionIdentifier

    /**
     * Description of the permission.
     * @example "Ability to modify organization data"
     */
    val description: String
}

/**
 * @d2 inherit
 */
@Serializable
data class Permission(
    override val id: PermissionId,
    override val identifier: PermissionIdentifier,
    override val description: String
): PermissionDTO, Privilege {
    override val type = PrivilegeType.PERMISSION
}
