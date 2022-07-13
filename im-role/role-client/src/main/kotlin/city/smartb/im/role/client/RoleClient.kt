package city.smartb.im.role.client

import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.role.domain.features.command.RoleAddCompositesCommand
import city.smartb.im.role.domain.features.command.RoleAddedCompositesEvent
import city.smartb.im.role.domain.features.command.RoleCreateCommand
import city.smartb.im.role.domain.features.command.RoleCreatedEvent
import city.smartb.im.role.domain.features.command.RoleUpdateCommand
import city.smartb.im.role.domain.features.command.RoleUpdatedEvent

class RoleClient(
    url: String,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, generateBearerToken) {

    suspend fun roleAddComposites(commands: List<RoleAddCompositesCommand>):
            List<RoleAddedCompositesEvent> = post("roleAddComposites", commands)

    suspend fun roleCreate(commands: List<RoleCreateCommand>):
            List<RoleCreatedEvent> = post("roleCreate", commands)

    suspend fun roleUpdate(commands: List<RoleUpdateCommand>):
            List<RoleUpdatedEvent> = post("roleUpdate", commands)
}
