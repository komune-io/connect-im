package city.smartb.im.f2.privilege.domain.role.model

import city.smartb.im.f2.privilege.domain.model.Privilege
import city.smartb.im.f2.privilege.domain.model.PrivilegeIdentifier
import city.smartb.im.f2.privilege.domain.model.PrivilegeType
import city.smartb.im.f2.privilege.domain.permission.model.PermissionIdentifier
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * @d2 hidden
 * @visual json "a0c76771-c5f9-4bde-8c30-901a22f570ba"
 */
typealias RoleId = String

/**
 * @d2 hidden
 */
typealias RoleIdentifier = PrivilegeIdentifier

/**
 * Named collection of permissions
 * @d2 model
 * @parent [city.smartb.im.privilege.domain.D2RolePage]
 * @order 10
 */
@JsExport
interface RoleDTO {
    /**
     * Generated id of the role.
     */
    val id: RoleId

    /**
     * Identifier of the role. Must be unique within a realm.
     * @example "tr_orchestrator"
     */
    val identifier: RoleIdentifier

    /**
     * Description of the role.
     * @example "Main organization role for orchestrators"
     */
    val description: String

    /**
     * List of entities the role can be applied to.
     * @example [["ORGANIZATION"]]
     */
    val targets: List<RoleTarget>

    /**
     * Human-readable translated names mapped by locale codes (e.g. "en", "fr", ...).
     * @example {
     *   "en": "Orchestrator",
     *   "fr": "Orchestrateur"
     * }
     */
    val locale: Map<String, String>

    /**
     * Allowed sub-roles mapped per target.
     * @example {
     *   "USER": ["tr_orchestrator_user", "tr_orchestrator_admin"],
     *   "API_KEY": ["tr_orchestrator_user", "tr_orchestrator_admin"]
     * }
     */
    val bindings: Map<RoleTarget, List<RoleIdentifier>>

    /**
     * Permissions granted to the role.
     * @example [["im_organization_read", "im_organization_write"]]
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
    override val targets: List<RoleTarget>,
    override val locale: Map<String, String>,
    override val bindings: Map<RoleTarget, List<RoleIdentifier>>,
    override val permissions: List<PermissionIdentifier>
): RoleDTO, Privilege {
    override val type = PrivilegeType.ROLE
}
