package city.smartb.im.core.privilege.domain.model

import city.smartb.im.commons.model.PermissionId
import city.smartb.im.commons.model.PermissionIdentifier
import city.smartb.im.commons.model.PrivilegeId
import city.smartb.im.commons.model.PrivilegeIdentifier
import city.smartb.im.commons.model.RoleId
import city.smartb.im.commons.model.RoleIdentifier
import kotlinx.serialization.Serializable

@Serializable
sealed interface Privilege {
    val id: PrivilegeId
    val identifier: PrivilegeIdentifier
    val description: String
    val type: PrivilegeType
}

@Serializable
data class PermissionModel(
    override val id: PermissionId,
    override val identifier: PermissionIdentifier,
    override val description: String
): Privilege {
    override val type = PrivilegeType.PERMISSION
}

@Serializable
data class RoleModel(
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
