//package city.smartb.im.organization.client
//
//import city.smartb.im.commons.ImMessage
//import city.smartb.im.organization.domain.features.command.OrganizationCreateCommand
//import city.smartb.im.organization.domain.features.command.OrganizationCreateResult
//import city.smartb.im.organization.domain.features.command.OrganizationUpdateCommand
//import city.smartb.im.organization.domain.features.command.OrganizationUpdateResult
//import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeQuery
//import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeResult
//import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
//import city.smartb.im.organization.domain.features.query.OrganizationGetResult
//import city.smartb.im.organization.domain.features.query.OrganizationPageQuery
//import city.smartb.im.organization.domain.features.query.OrganizationPageResult
//import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllQuery
//import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllResult
//import city.smartb.im.organization.api.OrganizationEndpoint
//import city.smartb.im.organization.api.service.OrganizationFinderService
//import i2.keycloak.master.domain.AuthRealm
//
/**
 * Currently not working because of the im-organization:organization-api dependency
 */
//class OrganizationClientSdk(
//    private val authRealm: AuthRealm,
//    private val organizationFinderService: OrganizationFinderService
//): OrganizationClient {
//
//    suspend fun organizationGet(command: List<OrganizationGetQuery>):
//            List<OrganizationGetResult> = organizationEndpoint.blblbl()
//
//    suspend fun organizationGetFromInsee(command: List<OrganizationGetFromInseeQuery>):
//            List<OrganizationGetFromInseeResult> = organizationEndpoint.blblbl()
//
//    suspend fun organizationPage(command: List<OrganizationPageQuery>):
//            List<OrganizationPageResult> = organizationEndpoint.blblbl()
//
//    override suspend fun organizationRefGetAll(command: List<OrganizationRefGetAllQuery>): List<OrganizationRefGetAllResult> {
//        return command.map { organizationFinderService.organizationRefGetAll(ImMessage(authRealm, authRealm.realmId, it)) }
//    }
//
//    suspend fun organizationCreate(command: List<OrganizationCreateCommand>):
//            List<OrganizationCreateResult> = organizationEndpoint.blblbl()
//
//    suspend fun organizationUpdate(command: List<OrganizationUpdateCommand>):
//            List<OrganizationUpdateResult> = organizationEndpoint.blblbl()
//}