package city.smartb.im.core.privilege.domain.command

import city.smartb.im.commons.model.PermissionIdentifier
import city.smartb.im.commons.model.PrivilegeIdentifier
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.core.privilege.domain.model.PrivilegeType
import city.smartb.im.core.privilege.domain.model.RoleTarget
import f2.dsl.cqrs.Event
import kotlinx.serialization.Serializable

@Serializable
sealed interface PrivilegeCoreDefineCommand {
    val identifier: PrivilegeIdentifier
    val description: String
    val type: PrivilegeType
}

@Serializable
data class PermissionCoreDefineCommand(
    override val identifier: PermissionIdentifier,
    override val description: String
): PrivilegeCoreDefineCommand {
    override val type = PrivilegeType.PERMISSION
}

@Serializable
data class RoleCoreDefineCommand(
    override val identifier: RoleIdentifier,
    override val description: String,
    val targets: List<RoleTarget>,
    val locale: Map<String, String>,
    val bindings: Map<RoleTarget, List<RoleIdentifier>>?,
    val permissions: List<PermissionIdentifier>?
): PrivilegeCoreDefineCommand {
    override val type = PrivilegeType.ROLE
}

@Serializable
data class PrivilegeCoreDefinedEvent(
    val identifier: PrivilegeIdentifier,
    val type: PrivilegeType
): Event
