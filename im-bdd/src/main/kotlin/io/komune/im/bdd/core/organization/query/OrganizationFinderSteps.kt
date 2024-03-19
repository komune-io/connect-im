package io.komune.im.bdd.core.organization.query

import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.organization.data.organization
import io.komune.im.f2.organization.api.OrganizationEndpoint
import io.komune.im.f2.organization.domain.model.Organization
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class OrganizationFinderSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    init {
        DataTableType { entry: Map<String, String> ->
            FetchByIdParams(
                identifier = entry.safeExtract("identifier")
            )
        }

        DataTableType { entry: Map<String, String> ->
            OrganizationFetchedParams(
                identifier = entry.safeExtract("identifier")
            )
        }

        Then("I should receive the organization") {
            step {
                assertOrganizationsFetched(listOf(context.organizationIds.lastUsedKey))
            }
        }

        Then("I should receive null instead of an organization") {
            step {
                assertOrganizationsFetched(emptyList())
            }
        }

        Then("I should receive a list of organizations:") { dataTable: DataTable ->
            step {
                dataTable.asList(OrganizationFetchedParams::class.java)
                    .map(OrganizationFetchedParams::identifier)
                    .let { assertOrganizationsFetched(it) }
            }
        }


    }

    private suspend fun assertOrganizationsFetched(identifiers: List<TestContextKey>) {
        val fetchedIds = context.fetched.organizations.map(Organization::id)
        val expectedIds = identifiers.map(context.organizationIds::safeGet).toTypedArray()
        Assertions.assertThat(fetchedIds).containsExactlyInAnyOrder(*expectedIds)

        val organizationAsserter = AssertionBdd.organization(keycloakClient())
        context.fetched.organizations.forEach { organization ->
            organizationAsserter.assertThatId(organization.id).matches(organization)
        }
    }

    private data class FetchByIdParams(
        val identifier: TestContextKey
    )

    private data class OrganizationFetchedParams(
        val identifier: TestContextKey
    )
}
