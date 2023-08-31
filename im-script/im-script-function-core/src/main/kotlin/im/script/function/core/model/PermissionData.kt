package im.script.function.core.model

import city.smartb.im.f2.privilege.domain.permission.command.PermissionDefineCommandDTOBase

data class PermissionData(
    val name: String,
    val description: String
) {
    fun toCommand() = PermissionDefineCommandDTOBase(
        identifier = name,
        description = description
    )
}
