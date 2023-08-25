package city.smartb.im.bdd.core.organization.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.organization.api.OrganizationEndpoint
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.TestContextKey

class OrganizationGetSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    init {
        DataTableType(::organizationGetParams)

        When ("I get an organization by ID") {
            step {
                organizationGet(organizationGetParams(null))
            }
        }

        When ("I get an organization by ID:") { params: OrganizationGetParams ->
            step {
                organizationGet(params)
            }
        }
    }

    private suspend fun organizationGet(params: OrganizationGetParams) {
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
