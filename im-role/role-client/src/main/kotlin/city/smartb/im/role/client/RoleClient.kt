package city.smartb.im.role.client

import city.smartb.im.role.domain.features.command.RoleAddCompositesCommand
import city.smartb.im.role.domain.features.command.RoleAddCompositesResult
import city.smartb.im.role.domain.features.command.RoleCreateCommand
import city.smartb.im.role.domain.features.command.RoleCreateResult
import city.smartb.im.role.domain.features.command.RoleUpdateCommand
import city.smartb.im.role.domain.features.command.RoleUpdateResult
import city.smartb.im.commons.http.ClientJvm

class RoleClient(
    url: String
): ClientJvm(url) {

    suspend fun roleAddComposites(command: List<RoleAddCompositesCommand>):
            List<RoleAddCompositesResult> = post("roleAddComposites", command)

    suspend fun roleCreate(command: List<RoleCreateCommand>):
            List<RoleCreateResult> = post("roleCreate", command)

    suspend fun roleUpdate(command: List<RoleUpdateCommand>):
            List<RoleUpdateResult> = post("organizationPage", command)
}
