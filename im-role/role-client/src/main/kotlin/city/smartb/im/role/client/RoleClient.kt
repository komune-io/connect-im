package city.smartb.im.role.client

import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.role.domain.features.command.RoleAddCompositesCommand
import city.smartb.im.role.domain.features.command.RoleAddCompositesResult
import city.smartb.im.role.domain.features.command.RoleCreateCommand
import city.smartb.im.role.domain.features.command.RoleCreateResult
import city.smartb.im.role.domain.features.command.RoleUpdateCommand
import city.smartb.im.role.domain.features.command.RoleUpdateResult

class RoleClient(
    url: String,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, generateBearerToken) {

    suspend fun roleAddComposites(commands: List<RoleAddCompositesCommand>):
            List<RoleAddCompositesResult> = post("roleAddComposites", commands)

    suspend fun roleCreate(commands: List<RoleCreateCommand>):
            List<RoleCreateResult> = post("roleCreate", commands)

    suspend fun roleUpdate(commands: List<RoleUpdateCommand>):
            List<RoleUpdateResult> = post("roleUpdate", commands)
}
