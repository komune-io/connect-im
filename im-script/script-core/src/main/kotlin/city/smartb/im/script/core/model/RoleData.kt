package city.smartb.im.script.core.model

import city.smartb.im.core.privilege.domain.model.PermissionIdentifier
import city.smartb.im.core.privilege.domain.model.RoleIdentifier
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.f2.privilege.domain.role.command.RoleDefineCommandDTOBase

data class RoleData(
    val name: String,
    val description: String,
    val targets: List<RoleTarget>?,
    val locale: Map<String, String>?,
    val bindings: Map<RoleTarget, List<RoleIdentifier>>?,
    val permissions: List<PermissionIdentifier>?
) {
    fun toCommand() = RoleDefineCommandDTOBase(
        identifier = name,
        description = description,
        targets = targets?.map(RoleTarget::name).orEmpty(),
        locale = locale.orEmpty(),
        bindings = bindings?.mapKeys { (target) -> target.name },
        permissions = permissions,
    )
}
