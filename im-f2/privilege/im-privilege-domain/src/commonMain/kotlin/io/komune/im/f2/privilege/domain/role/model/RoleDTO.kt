package io.komune.im.f2.privilege.domain.role.model

import io.komune.im.commons.model.PermissionIdentifier
import io.komune.im.commons.model.RoleId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.core.privilege.domain.model.PrivilegeType
import io.komune.im.f2.privilege.domain.model.PrivilegeDTO
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Named collection of permissions
 * @d2 model
 * @parent [io.komune.im.f2.privilege.domain.D2RolePage]
 * @order 10
 */
@JsExport
interface RoleDTO: PrivilegeDTO {
    /**
     * Generated id of the role.
     */
    override val id: RoleId

    /**
     * @ref [PrivilegeDTO.type]
     * @example "ROLE"
     */
    override val type: String

    /**
     * Identifier of the role. Must be unique within a realm.
     * @example "tr_orchestrator"
     */
    override val identifier: RoleIdentifier

    /**
     * Description of the role.
     * @example "Main organization role for orchestrators"
     */
    override val description: String

    /**
     * List of entities the role can be applied to. See [RoleTarget][io.komune.im.core.privilege.domain.model.RoleTarget]
     * @example [["ORGANIZATION"]]
     */
    val targets: List<String>

    /**
     * Human-readable translated names mapped by locale codes (e.g. "en", "fr", ...).
     * @example {
     *   "en": "Orchestrator",
     *   "fr": "Orchestrateur"
     * }
     */
    val locale: Map<String, String>

    /**
     * Allowed sub-roles mapped per [target][io.komune.im.core.privilege.domain.model.RoleTarget].
     * @example {
     *   "USER": [{
     *      "id": "2a384297-490c-4097-8bf0-ee5e7a233e16",
     *      "identifier": "tr_orchestrator_admin",
     *      "description": "Admin role for orchestrator organization members",
     *      "targets": [["USER", "API_KEY"]],
     *      "locale": { "en": "Admin", "fr": "Admin" },
     *      "bindings": {},
     *      "permissions": [["im_organization_read", "im_organization_write", "im_user_read", "im_user_write"]]
     *   }, {
     *      "id": "3021ebe8-1b28-44ef-97c9-2a6a34f54042",
     *      "identifier": "tr_orchestrator_user",
     *      "description": "User role for orchestrator organization members",
     *      "targets": [["USER", "API_KEY"]],
     *      "locale": { "en": "User", "fr": "Utilisateur" },
     *      "bindings": {},
     *      "permissions": [["im_organization_read", "im_user_read"]]
     *   }],
     *   "API_KEY": [{
     *      "id": "2a384297-490c-4097-8bf0-ee5e7a233e16",
     *      "identifier": "tr_orchestrator_admin",
     *      "description": "Admin role for orchestrator organization members",
     *      "targets": [["USER", "API_KEY"]],
     *      "locale": { "en": "Admin", "fr": "Admin" },
     *      "bindings": {},
     *      "permissions": [["im_organization_read", "im_organization_write", "im_user_read", "im_user_write"]]
     *   }, {
     *      "id": "3021ebe8-1b28-44ef-97c9-2a6a34f54042",
     *      "identifier": "tr_orchestrator_user",
     *      "description": "User role for orchestrator organization members",
     *      "targets": [["USER", "API_KEY"]],
     *      "locale": { "en": "User", "fr": "Utilisateur" },
     *      "bindings": {},
     *      "permissions": [["im_organization_read", "im_user_read"]]
     *   }]
     * }
     */
    val bindings: Map<String, List<RoleDTO>>

    /**
     * Permissions granted to the role.
     * @example [[]]
     */
    val permissions: List<PermissionIdentifier>
}

/**
 * @d2 inherit
 */
@Serializable
data class Role(
    override val id: RoleId,
    override val identifier: RoleIdentifier,
    override val description: String,
    override val targets: List<String>,
    override val locale: Map<String, String>,
    override val bindings: Map<String, List<Role>>,
    override val permissions: List<PermissionIdentifier>,
): RoleDTO {
    override val type = PrivilegeType.ROLE.name
}
