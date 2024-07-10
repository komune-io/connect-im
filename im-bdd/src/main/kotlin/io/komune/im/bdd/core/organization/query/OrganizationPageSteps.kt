package io.komune.im.bdd.core.organization.query

import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.f2.organization.api.OrganizationEndpoint
import io.komune.im.f2.organization.domain.query.OrganizationPageQuery
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class OrganizationPageSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    init {
        DataTableType(::organizationPageParams)

        When ("I get a page of organizations") {
            step {
                fetchOrganizationPage(organizationPageParams(null))
            }
        }

        When ("I get a page of organizations:") { params: OrganizationPageParams ->
            step {
                fetchOrganizationPage(params)
            }
        }
    }

    private suspend fun fetchOrganizationPage(params: OrganizationPageParams) {
        context.fetched.organizations = OrganizationPageQuery(
            name = params.name,
            role = params.role,
            withDisabled = params.withDisable,
            offset = params.offset ?: 0,
            limit = params.limit ?: Int.MAX_VALUE,
            attributes = null
        ).invokeWith(organizationEndpoint.organizationPage()).items
    }

    private fun organizationPageParams(entry: Map<String, String>?) = OrganizationPageParams(
        name = entry?.get("name"),
        role = entry?.get("role"),
//        attributes = entry?.get("attr"),
        withDisable = entry?.get("withDisabled").toBoolean(),
        offset = entry?.get("offset")?.toInt(),
        limit = entry?.get("limit")?.toInt(),
    )

    private data class OrganizationPageParams(
        val name: String?,
        val role: String?,
//        val attributes: Map<String, String>?,
        val withDisable: Boolean,
        val offset: Int?,
        val limit: Int?
    )
}
