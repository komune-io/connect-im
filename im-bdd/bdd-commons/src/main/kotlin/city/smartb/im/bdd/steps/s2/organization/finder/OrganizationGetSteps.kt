package city.smartb.im.bdd.steps.s2.organization.finder

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.organization.api.OrganizationEndpoint
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class OrganizationGetSteps: En, CucumberStepsDefinition() {

    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    init {
        DataTableType(::organizationGetParams)

        When ("I get an organization by its ID") {
            step {
                fetchOrganizationGet(organizationGetParams(null))
            }
        }

        When ("I get an organization by its ID:") { params: OrganizationGetParams ->
            step {
                fetchOrganizationGet(params)
            }
        }
    }

    private suspend fun fetchOrganizationGet(params: OrganizationGetParams) {
        context.fetched.organizations = listOfNotNull(
            OrganizationGetQuery(
                id = params.identifier
            )
                .invokeWith(organizationEndpoint.organizationGet())
                .item
        )
    }

    private fun organizationGetParams(entry: Map<String, String>?) = OrganizationGetParams(
        identifier = entry?.get("identifier") ?: context.organizationIds.lastUsed
    )

    private data class OrganizationGetParams(
        val identifier: TestContextKey
    )
}
