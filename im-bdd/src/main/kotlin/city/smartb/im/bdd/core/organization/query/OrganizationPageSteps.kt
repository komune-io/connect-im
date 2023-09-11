package city.smartb.im.bdd.core.organization.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.f2.organization.api.OrganizationEndpoint
import city.smartb.im.f2.organization.domain.query.OrganizationPageQuery
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
            name = params.search,
            role = params.role,
            withDisabled = params.withDisable,
            offset = params.offset ?: 0,
            limit = params.limit ?: Int.MAX_VALUE,
            attributes = null
        ).invokeWith(organizationEndpoint.organizationPage()).items
    }

    private fun organizationPageParams(entry: Map<String, String>?) = OrganizationPageParams(
        search = entry?.get("search"),
        role = entry?.get("role"),
//        attributes = entry?.get("attr"),
        withDisable = entry?.get("withDisabled").toBoolean(),
        offset = entry?.get("offset")?.toInt(),
        limit = entry?.get("limit")?.toInt(),
    )

    private data class OrganizationPageParams(
        val search: String?,
        val role: String?,
//        val attributes: Map<String, String>?,
        val withDisable: Boolean,
        val offset: Int?,
        val limit: Int?
    )
}
