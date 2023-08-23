package city.smartb.im.privilege.client

import city.smartb.im.commons.http.ClientBuilder
import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.commons.http.HttpClientBuilderJvm
import city.smartb.im.privilege.domain.role.command.RoleAddCompositesCommand
import city.smartb.im.privilege.domain.role.command.RoleAddedCompositesEvent
import city.smartb.im.privilege.domain.role.command.RoleCreatedEvent
import city.smartb.im.privilege.domain.role.command.RoleDefineCommand
import city.smartb.im.privilege.domain.role.command.RoleUpdateCommand
import city.smartb.im.privilege.domain.role.command.RoleUpdatedEvent
import city.smartb.im.privilege.domain.role.query.RoleGetByIdQuery
import city.smartb.im.privilege.domain.role.query.RoleGetByNameQuery
import city.smartb.im.privilege.domain.role.query.RoleGetByNameResult

class PrivilegeClient(
    url: String,
    httpClientBuilder: ClientBuilder = HttpClientBuilderJvm,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, httpClientBuilder, generateBearerToken) {

    suspend fun roleAddComposites(commands: List<RoleAddCompositesCommand>):
            List<RoleAddedCompositesEvent> = post("roleAddComposites", commands)

    suspend fun roleCreate(commands: List<RoleDefineCommand>):
            List<RoleCreatedEvent> = post("roleCreate", commands)

    suspend fun roleUpdate(commands: List<RoleUpdateCommand>):
            List<RoleUpdatedEvent> = post("roleUpdate", commands)


    suspend fun roleGetById(commands: List<RoleGetByIdQuery>):
            List<RoleGetByIdQuery> = post("roleGetById", commands)

    suspend fun roleGetByName(commands: List<RoleGetByNameQuery>):
            List<RoleGetByNameResult> = post("roleGetByName", commands)

}
