package city.smartb.im.organization.client

import city.smartb.im.commons.http.ClientBuilder
import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.commons.http.HttpClientBuilderJvm
import city.smartb.im.organization.domain.features.command.OrganizationCreateCommand
import city.smartb.im.organization.domain.features.command.OrganizationCreatedEvent
import city.smartb.im.organization.domain.features.command.OrganizationUpdateCommand
import city.smartb.im.organization.domain.features.command.OrganizationUpdatedResult
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeQuery
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeResult
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.features.query.OrganizationGetResult
import city.smartb.im.organization.domain.features.query.OrganizationPageQuery
import city.smartb.im.organization.domain.features.query.OrganizationPageResult
import city.smartb.im.organization.domain.features.query.OrganizationRefListQuery
import city.smartb.im.organization.domain.features.query.OrganizationRefListResult
import city.smartb.im.organization.domain.model.OrganizationDTO

class OrganizationClient<MODEL: OrganizationDTO>(
    url: String,
    httpClientBuilder: ClientBuilder = HttpClientBuilderJvm,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, httpClientBuilder, generateBearerToken) {

    suspend inline fun <reified OUT : MODEL> organizationGet(queries: List<OrganizationGetQuery>):
            List<OrganizationGetResult<OUT>> = post("organizationGet",  queries)

    suspend fun organizationGetFromInsee(queries: List<OrganizationGetFromInseeQuery>):
            List<OrganizationGetFromInseeResult> = post("organizationGetFromInsee", queries)

    suspend inline fun <reified OUT : MODEL> organizationPage(queries: List<OrganizationPageQuery>):
            List<OrganizationPageResult<OUT>> = post("organizationPage", queries)

    suspend fun organizationRefList(queries: List<OrganizationRefListQuery>):
            List<OrganizationRefListResult> = post("organizationRefList", queries)

    suspend fun organizationCreate(commands: List<OrganizationCreateCommand>):
            List<OrganizationCreatedEvent> = post("organizationCreate", commands)

    suspend fun organizationUpdate(commands: List<OrganizationUpdateCommand>):
            List<OrganizationUpdatedResult> = post("organizationUpdate", commands)
}
