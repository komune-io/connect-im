package city.smartb.im.organization.client

import city.smartb.im.organization.domain.features.command.OrganizationCreateCommand
import city.smartb.im.organization.domain.features.command.OrganizationCreateResult
import city.smartb.im.organization.domain.features.command.OrganizationUpdateCommand
import city.smartb.im.organization.domain.features.command.OrganizationUpdateResult
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeQuery
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeResult
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.features.query.OrganizationGetResult
import city.smartb.im.organization.domain.features.query.OrganizationPageQuery
import city.smartb.im.organization.domain.features.query.OrganizationPageResult
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllQuery
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllResult
import city.smartb.im.commons.http.ClientJvm

class OrganizationClient(
    url: String
): ClientJvm(url) {

    suspend fun organizationGet(command: List<OrganizationGetQuery>):
            List<OrganizationGetResult> = post("organizationGet", command)

    suspend fun organizationGetFromInsee(command: List<OrganizationGetFromInseeQuery>):
            List<OrganizationGetFromInseeResult> = post("organizationGetFromInsee", command)

    suspend fun organizationPage(command: List<OrganizationPageQuery>):
            List<OrganizationPageResult> = post("organizationPage", command)

    suspend fun organizationRefGetAll(command: List<OrganizationRefGetAllQuery>):
            List<OrganizationRefGetAllResult> = post("organizationRefGetAll", command)

    suspend fun organizationCreate(command: List<OrganizationCreateCommand>):
            List<OrganizationCreateResult> = post("organizationCreate", command)

    suspend fun organizationUpdate(command: List<OrganizationUpdateCommand>):
            List<OrganizationUpdateResult> = post("organizationUpdate", command)
}
