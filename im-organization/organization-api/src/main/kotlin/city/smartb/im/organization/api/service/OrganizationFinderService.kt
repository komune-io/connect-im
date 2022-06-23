package city.smartb.im.organization.api.service

import city.smartb.im.api.auth.ImAuthenticationResolver
import city.smartb.im.organization.api.model.toOrganization
import city.smartb.im.organization.api.model.toOrganizationRef
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeQuery
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeResult
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.features.query.OrganizationGetResult
import city.smartb.im.organization.domain.features.query.OrganizationPageQuery
import city.smartb.im.organization.domain.features.query.OrganizationPageResult
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllQuery
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllResult
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.invoke
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetQuery
import i2.keycloak.f2.group.domain.features.query.GroupPageFunction
import i2.keycloak.f2.group.domain.features.query.GroupPageQuery
import i2.keycloak.f2.group.domain.model.GroupModel
import org.springframework.stereotype.Service

@Service
class OrganizationFinderService(
    private val inseeHttpClient: InseeHttpClient,
    private val groupGetFunction: GroupGetFunction,
    private val groupPageFunction: GroupPageFunction,
    private val authenticationResolver: ImAuthenticationResolver
) {

    suspend fun organizationGet(query: OrganizationGetQuery): OrganizationGetResult {
        return groupGetFunction.invoke(query.toGroupGetByIdQuery())
            .item
            ?.toOrganization()
            .let(::OrganizationGetResult)
    }

    suspend fun organizationGetFromInsee(query: OrganizationGetFromInseeQuery)
        : OrganizationGetFromInseeResult {

        val organizationDetails = try {
            inseeHttpClient.getOrganizationBySiret(query.siret)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return organizationDetails?.etablissement
            ?.toOrganization()
            .let(::OrganizationGetFromInseeResult)
    }

    suspend fun organizationPage(query: OrganizationPageQuery): OrganizationPageResult {
        val result = groupPageFunction.invoke(query.toGroupGetAllQuery())

        return OrganizationPageResult(
            items = result.page.items.map(GroupModel::toOrganization),
            total = result.page.total
        )
    }

    suspend fun organizationRefGetAll(query: OrganizationRefGetAllQuery): OrganizationRefGetAllResult {
        return groupPageFunction.invoke(query.toGroupGetAllQuery())
            .page
            .items
            .map(GroupModel::toOrganizationRef)
            .let(::OrganizationRefGetAllResult)
    }

    private suspend fun OrganizationRefGetAllQuery.toGroupGetAllQuery(): GroupPageQuery {
        val auth = authenticationResolver.getAuth()
        return GroupPageQuery(
            name = null,
            role = null,
            page = PagePagination(
                page = null,
                size = null
            ),
            realmId = auth.realmId,
            auth = auth
        )
    }

    private suspend fun OrganizationPageQuery.toGroupGetAllQuery(): GroupPageQuery {
        val auth = authenticationResolver.getAuth()
        return GroupPageQuery(
            name = name,
            role = role,
            page = PagePagination(
                page = page,
                size = size
            ),
            realmId = auth.realmId,
            auth = auth
        )
    }

    private suspend fun OrganizationGetQuery.toGroupGetByIdQuery(): GroupGetQuery {
        val auth = authenticationResolver.getAuth()
        return GroupGetQuery(
            id = id,
            realmId = auth.realmId,
            auth = auth
        )
    }
}
