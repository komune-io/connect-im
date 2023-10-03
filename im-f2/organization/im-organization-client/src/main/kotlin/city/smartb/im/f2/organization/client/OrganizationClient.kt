package city.smartb.im.f2.organization.client

import city.smartb.im.commons.http.ClientBuilder
import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.commons.http.HttpClientBuilderJvm
import city.smartb.im.f2.organization.domain.command.OrganizationCreateCommand
import city.smartb.im.f2.organization.domain.command.OrganizationCreatedEvent
import city.smartb.im.f2.organization.domain.command.OrganizationUpdateCommand
import city.smartb.im.f2.organization.domain.command.OrganizationUpdatedResult
import city.smartb.im.f2.organization.domain.query.OrganizationGetFromInseeQuery
import city.smartb.im.f2.organization.domain.query.OrganizationGetFromInseeResult
import city.smartb.im.f2.organization.domain.query.OrganizationGetQuery
import city.smartb.im.f2.organization.domain.query.OrganizationGetResult
import city.smartb.im.f2.organization.domain.query.OrganizationPageQuery
import city.smartb.im.f2.organization.domain.query.OrganizationPageResult
import city.smartb.im.f2.organization.domain.query.OrganizationRefListQuery
import city.smartb.im.f2.organization.domain.query.OrganizationRefListResult

class OrganizationClient(
    url: String,
    httpClientBuilder: ClientBuilder = HttpClientBuilderJvm,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, httpClientBuilder, generateBearerToken) {

    suspend fun organizationGet(queries: List<OrganizationGetQuery>):
            List<OrganizationGetResult> = post("organizationGet",  queries)

    suspend fun organizationGetFromInsee(queries: List<OrganizationGetFromInseeQuery>):
            List<OrganizationGetFromInseeResult> = post("organizationGetFromInsee", queries)

    suspend fun organizationPage(queries: List<OrganizationPageQuery>):
            List<OrganizationPageResult> = post("organizationPage", queries)

    suspend fun organizationRefList(queries: List<OrganizationRefListQuery>):
            List<OrganizationRefListResult> = post("organizationRefList", queries)

    suspend fun organizationCreate(commands: List<OrganizationCreateCommand>):
            List<OrganizationCreatedEvent> = post("organizationCreate", commands)

    suspend fun organizationUpdate(commands: List<OrganizationUpdateCommand>):
            List<OrganizationUpdatedResult> = post("organizationUpdate", commands)
}
