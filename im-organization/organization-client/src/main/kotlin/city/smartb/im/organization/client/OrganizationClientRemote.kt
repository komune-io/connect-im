package city.smartb.im.organization.client

import city.smartb.im.commons.ImMessage
import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllQuery
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllResult
import i2.keycloak.master.domain.AuthRealm

class OrganizationClientRemote(
    url: String,
    generateBearerToken: suspend () -> String? = { null },
    private val authRealm: AuthRealm
): ClientJvm(url, generateBearerToken), OrganizationClient {

//    suspend fun organizationGet(command: OrganizationGetQuery): OrganizationGetResult =
//        post("organizationGet", ImMessage(authRealm, authRealm.realmId, command))
//
//    suspend fun organizationGetFromInsee(command: List<OrganizationGetFromInseeQuery>):
//            List<OrganizationGetFromInseeResult> = post("organizationGetFromInsee", command)
//
//    suspend fun organizationPage(command: List<OrganizationPageQuery>):
//            List<OrganizationPageResult> = post("organizationPage", command)

    override suspend fun organizationRefGetAll(command: List<OrganizationRefGetAllQuery>): List<OrganizationRefGetAllResult> =
        post("organizationRefGetAll", command.map {ImMessage(authRealm, authRealm.realmId, it)})

//    suspend fun organizationCreate(command: List<OrganizationCreateCommand>):
//            List<OrganizationCreateResult> = post("organizationCreate", command)
//
//    suspend fun organizationUpdate(command: List<OrganizationUpdateCommand>):
//            List<OrganizationUpdateResult> = post("organizationUpdate", command)
}