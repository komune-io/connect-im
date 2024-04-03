package io.komune.im.script.core.model

import io.komune.im.f2.privilege.domain.permission.command.PermissionDefineCommand

data class PermissionData(
    val name: String,
    val description: String
) {
    fun toCommand() = PermissionDefineCommand(
        identifier = name,
        description = description
    )
}
