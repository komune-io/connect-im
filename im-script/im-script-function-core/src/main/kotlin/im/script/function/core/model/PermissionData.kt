package im.script.function.core.model

import city.smartb.im.commons.model.RealmId
import city.smartb.im.f2.privilege.domain.permission.command.PermissionDefineCommandDTOBase

data class PermissionData(
    val name: String,
    val description: String
) {
    fun toCommand(realmId: RealmId?) = PermissionDefineCommandDTOBase(
        realmId = realmId,
        identifier = name,
        description = description
    )
}
