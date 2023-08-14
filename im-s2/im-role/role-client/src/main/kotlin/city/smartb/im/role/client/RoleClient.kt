package city.smartb.im.role.client

import city.smartb.im.commons.http.ClientBuilder
import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.commons.http.HttpClientBuilderJvm
import city.smartb.im.role.domain.features.command.RoleAddCompositesCommand
import city.smartb.im.role.domain.features.command.RoleAddedCompositesEvent
import city.smartb.im.role.domain.features.command.RoleCreateCommand
import city.smartb.im.role.domain.features.command.RoleCreatedEvent
import city.smartb.im.role.domain.features.command.RoleUpdateCommand
import city.smartb.im.role.domain.features.command.RoleUpdatedEvent
import city.smartb.im.role.domain.features.query.RoleGetByIdQuery
import city.smartb.im.role.domain.features.query.RoleGetByNameQuery
import city.smartb.im.role.domain.features.query.RoleGetByNameResult

class RoleClient(
    url: String,
    httpClientBuilder: ClientBuilder = HttpClientBuilderJvm,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, httpClientBuilder, generateBearerToken) {

    suspend fun roleAddComposites(commands: List<RoleAddCompositesCommand>):
            List<RoleAddedCompositesEvent> = post("roleAddComposites", commands)

    suspend fun roleCreate(commands: List<RoleCreateCommand>):
            List<RoleCreatedEvent> = post("roleCreate", commands)

    suspend fun roleUpdate(commands: List<RoleUpdateCommand>):
            List<RoleUpdatedEvent> = post("roleUpdate", commands)


    suspend fun roleGetById(commands: List<RoleGetByIdQuery>):
            List<RoleGetByIdQuery> = post("roleGetById", commands)

    suspend fun roleGetByName(commands: List<RoleGetByNameQuery>):
            List<RoleGetByNameResult> = post("roleGetByName", commands)

}
