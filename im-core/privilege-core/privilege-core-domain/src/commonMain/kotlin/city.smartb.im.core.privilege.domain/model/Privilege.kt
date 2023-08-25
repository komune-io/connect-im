package city.smartb.im.core.privilege.domain.model

import kotlinx.serialization.Serializable

typealias PrivilegeId = String
typealias PrivilegeIdentifier = String

/**
 * @d2 hidden
 * @visual json "868044f8-1852-43eb-897d-067bc4631396"
 */
typealias PermissionId = PrivilegeId

/**
 * @d2 hidden
 */
typealias PermissionIdentifier = PrivilegeIdentifier

/**
 * @d2 hidden
 * @visual json "a0c76771-c5f9-4bde-8c30-901a22f570ba"
 */
typealias RoleId = PrivilegeId

/**
 * @d2 hidden
 */
typealias RoleIdentifier = PrivilegeIdentifier

@Serializable
sealed interface Privilege {
    val id: PrivilegeId
    val identifier: PrivilegeIdentifier
    val description: String
    val type: PrivilegeType
}

@Serializable
data class Permission(
    override val id: PermissionId,
    override val identifier: PermissionIdentifier,
    override val description: String
): Privilege {
    override val type = PrivilegeType.PERMISSION
}

@Serializable
data class Role(
    override val id: RoleId,
    override val identifier: RoleIdentifier,
    override val description: String,
    val targets: List<RoleTarget>,
    val locale: Map<String, String>,
    val bindings: Map<RoleTarget, List<RoleIdentifier>>,
    val permissions: List<PermissionIdentifier>
): Privilege {
    override val type = PrivilegeType.ROLE
}
