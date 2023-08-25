package city.smartb.im.core.privilege.domain.command

import city.smartb.im.commons.model.ImCommand
import city.smartb.im.core.privilege.domain.model.PermissionIdentifier
import city.smartb.im.core.privilege.domain.model.PrivilegeIdentifier
import city.smartb.im.core.privilege.domain.model.PrivilegeType
import city.smartb.im.core.privilege.domain.model.RoleIdentifier
import city.smartb.im.core.privilege.domain.model.RoleTarget
import f2.dsl.cqrs.Event
import kotlinx.serialization.Serializable

@Serializable
sealed interface PrivilegeDefineCommand: ImCommand {
    override val realmId: String?
    val identifier: PrivilegeIdentifier
    val description: String
    val type: PrivilegeType
}

@Serializable
data class PermissionDefineCommand(
    override val realmId: String? = null,
    override val identifier: PermissionIdentifier,
    override val description: String
): PrivilegeDefineCommand {
    override val type = PrivilegeType.PERMISSION
}

@Serializable
data class RoleDefineCommand(
    override val realmId: String? = null,
    override val identifier: RoleIdentifier,
    override val description: String,
    val targets: List<RoleTarget>,
    val locale: Map<String, String>,
    val bindings: Map<RoleTarget, List<RoleIdentifier>>?,
    val permissions: List<PermissionIdentifier>?
): PrivilegeDefineCommand {
    override val type = PrivilegeType.ROLE
}

@Serializable
data class PrivilegeDefinedEvent(
    val identifier: PrivilegeIdentifier,
    val type: PrivilegeType
): Event
