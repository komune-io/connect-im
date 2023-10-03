package city.smartb.im.script.core.model

import city.smartb.im.f2.privilege.domain.permission.command.PermissionDefineCommand

data class PermissionData(
    val name: String,
    val description: String
) {
    fun toCommand() = PermissionDefineCommand(
        identifier = name,
        description = description
    )
}
