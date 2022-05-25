package city.smartb.im.organization.api.service

import city.smartb.im.commons.ImMessage
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
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import org.springframework.stereotype.Service

@Service
class OrganizationFinderService(
    private val inseeHttpClient: InseeHttpClient,
    private val groupGetFunction: GroupGetFunction,
    private val groupPageFunction: GroupPageFunction
) {

    suspend fun organizationGet(query: ImMessage<OrganizationGetQuery>): OrganizationGetResult {
        return groupGetFunction.invoke(query.toGroupGetByIdQuery())
            .item
            ?.toOrganization()
            .let(::OrganizationGetResult)
    }

    suspend fun organizationGetFromInsee(query: ImMessage<OrganizationGetFromInseeQuery>)
        : OrganizationGetFromInseeResult {

        val organizationDetails = try {
            inseeHttpClient.getOrganizationBySiret(query.payload.siret)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return organizationDetails?.etablissement
            ?.toOrganization()
            .let(::OrganizationGetFromInseeResult)
    }

    suspend fun organizationPage(query: ImMessage<OrganizationPageQuery>): OrganizationPageResult {
        val result = groupPageFunction.invoke(query.toGroupGetAllQuery())

        return OrganizationPageResult(
            items = result.page.items.map(GroupModel::toOrganization),
            total = result.page.total
        )
    }

    suspend fun organizationRefGetAll(query: ImMessage<OrganizationRefGetAllQuery>): OrganizationRefGetAllResult {
        println("into finder")
        return groupPageFunction.invoke(query.payload.toGroupGetAllQuery(query.authRealm, query.realmId))
            .page
            .items
            .map(GroupModel::toOrganizationRef)
            .let(::OrganizationRefGetAllResult)
    }

    private fun OrganizationRefGetAllQuery.toGroupGetAllQuery(authRealm: AuthRealm, realmId: RealmId) = GroupPageQuery(
        name = null,
        role = null,
        page = PagePagination(
            page = null,
            size = null
        ),
        realmId = realmId,
        auth = authRealm
    )

    private fun ImMessage<OrganizationPageQuery>.toGroupGetAllQuery() = GroupPageQuery(
        name = payload.name,
        role = payload.role,
        page = PagePagination(
            page = payload.page,
            size = payload.size
        ),
        realmId = realmId,
        auth = authRealm
    )

    private fun ImMessage<OrganizationGetQuery>.toGroupGetByIdQuery() = GroupGetQuery(
        id = payload.id,
        realmId = realmId,
        auth = authRealm
    )
}
