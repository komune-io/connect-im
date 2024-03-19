package io.komune.im.f2.organization.client

import io.komune.im.commons.http.ClientBuilder
import io.komune.im.commons.http.ClientJvm
import io.komune.im.commons.http.HttpClientBuilderJvm
import io.komune.im.f2.organization.domain.command.OrganizationCreateCommand
import io.komune.im.f2.organization.domain.command.OrganizationCreatedEvent
import io.komune.im.f2.organization.domain.command.OrganizationUpdateCommand
import io.komune.im.f2.organization.domain.command.OrganizationUpdatedResult
import io.komune.im.f2.organization.domain.query.OrganizationGetFromInseeQuery
import io.komune.im.f2.organization.domain.query.OrganizationGetFromInseeResult
import io.komune.im.f2.organization.domain.query.OrganizationGetQuery
import io.komune.im.f2.organization.domain.query.OrganizationGetResult
import io.komune.im.f2.organization.domain.query.OrganizationPageQuery
import io.komune.im.f2.organization.domain.query.OrganizationPageResult
import io.komune.im.f2.organization.domain.query.OrganizationRefListQuery
import io.komune.im.f2.organization.domain.query.OrganizationRefListResult

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
