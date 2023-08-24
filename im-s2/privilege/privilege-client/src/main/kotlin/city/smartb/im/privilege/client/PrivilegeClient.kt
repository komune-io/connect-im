package city.smartb.im.privilege.client

import city.smartb.im.commons.http.ClientBuilder
import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.commons.http.HttpClientBuilderJvm

class PrivilegeClient(
    url: String,
    httpClientBuilder: ClientBuilder = HttpClientBuilderJvm,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, httpClientBuilder, generateBearerToken) {

//    suspend fun roleAddComposites(commands: List<RoleAddCompositesCommand>):
//            List<RoleAddedCompositesEvent> = post("roleAddComposites", commands)
//
//    suspend fun roleCreate(commands: List<RoleDefineCommand>):
//            List<RoleDefinedEvent> = post("roleCreate", commands)
//
//    suspend fun roleUpdate(commands: List<RoleUpdateCommand>):
//            List<RoleUpdatedEvent> = post("roleUpdate", commands)
//
//
//    suspend fun roleGetById(commands: List<RoleGetByIdQuery>):
//            List<RoleGetByIdQuery> = post("roleGetById", commands)
//
//    suspend fun roleGetByName(commands: List<RoleGetByNameQuery>):
//            List<RoleGetByNameResult> = post("roleGetByName", commands)

}
