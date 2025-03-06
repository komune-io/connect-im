package io.komune.im.script.core.model

import io.komune.im.commons.model.PermissionIdentifier
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.core.privilege.domain.model.RoleTarget
import io.komune.im.f2.privilege.domain.role.command.RoleDefineCommand

data class RoleData(
    val name: String,
    val description: String,
    val targets: List<RoleTarget>?,
    val locale: Map<String, String>?,
    val bindings: Map<RoleTarget, List<RoleIdentifier>>?,
    val permissions: List<PermissionIdentifier>?,
) {
    fun toCommand() = RoleDefineCommand(
        identifier = name,
        description = description,
        targets = targets?.map(RoleTarget::name).orEmpty(),
        locale = locale.orEmpty(),
        bindings = bindings?.mapKeys { (target) -> target.name },
        permissions = permissions,
    )
}
