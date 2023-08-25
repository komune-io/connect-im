package city.smartb.im.bdd.core.organization.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.organization.api.OrganizationEndpoint
import city.smartb.im.organization.domain.features.query.OrganizationPageQuery
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
            search = params.search,
            role = params.role,
            withDisabled = params.withDisable,
            page = params.page,
            size = params.size,
            attributes = null
        ).invokeWith(organizationEndpoint.organizationPage()).items
    }

    private fun organizationPageParams(entry: Map<String, String>?) = OrganizationPageParams(
        search = entry?.get("search"),
        role = entry?.get("role"),
//        attributes = entry?.get("attr"),
        withDisable = entry?.get("withDisabled").toBoolean(),
        page = entry?.get("page")?.toInt(),
        size = entry?.get("size")?.toInt(),
    )

    private data class OrganizationPageParams(
        val search: String?,
        val role: String?,
//        val attributes: Map<String, String>?,
        val withDisable: Boolean,
        val page: Int?,
        val size: Int?
    )
}
