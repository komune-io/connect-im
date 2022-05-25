package city.smartb.im.organization.client

import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllQuery
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllResult

interface OrganizationClient {
//    suspend fun organizationGet(command: OrganizationGetQuery): OrganizationGetResult
//    fun organizationGetFromInsee(command: List<OrganizationGetFromInseeQuery>): List<OrganizationGetFromInseeResult>
//    fun organizationPage(command: List<OrganizationPageQuery>): List<OrganizationPageResult>
    suspend fun organizationRefGetAll(command: List<OrganizationRefGetAllQuery>): List<OrganizationRefGetAllResult>
//    fun organizationCreate(command: List<OrganizationCreateCommand>): List<OrganizationCreateResult>
//    fun organizationUpdate(command: List<OrganizationUpdateCommand>): List<OrganizationUpdateResult>
}
