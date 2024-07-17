package io.komune.im.core.privilege.domain.model

import io.komune.im.commons.model.FeatureId
import io.komune.im.commons.model.FeatureIdentifier
import io.komune.im.commons.model.PermissionId
import io.komune.im.commons.model.PermissionIdentifier
import io.komune.im.commons.model.PrivilegeId
import io.komune.im.commons.model.PrivilegeIdentifier
import io.komune.im.commons.model.RoleId
import io.komune.im.commons.model.RoleIdentifier
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
    override val description: String,
    val features: List<List<FeatureIdentifier>>?
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

@Serializable
data class FeatureModel(
    override val id: FeatureId,
    override val identifier: FeatureIdentifier,
    override val description: String
): Privilege {
    override val type = PrivilegeType.FEATURE
}
