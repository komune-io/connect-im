package city.smartb.im.f2.privilege.domain.permission.model

import city.smartb.im.core.privilege.domain.model.PermissionId
import city.smartb.im.core.privilege.domain.model.PermissionIdentifier
import city.smartb.im.core.privilege.domain.model.PrivilegeType
import city.smartb.im.f2.privilege.domain.model.PrivilegeDTO
import city.smartb.im.f2.privilege.domain.model.PrivilegeIdentifier
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
}

/**
 * @d2 inherit
 */
@Serializable
data class PermissionDTOBase(
    override val id: PermissionId,
    override val identifier: PermissionIdentifier,
    override val description: String
): PermissionDTO {
    override val type = PrivilegeType.PERMISSION.name
}
