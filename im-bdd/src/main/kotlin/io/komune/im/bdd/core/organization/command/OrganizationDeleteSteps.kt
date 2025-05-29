package io.komune.im.bdd.core.organization.command

import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.organization.data.organization
import io.komune.im.core.organization.domain.command.OrganizationCoreDeleteCommand
import io.komune.im.f2.organization.api.OrganizationEndpoint
import io.komune.im.f2.organization.domain.command.OrganizationDeleteCommand
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey

class OrganizationDeleteSteps: En, ImCucumberStepsDefinition() {
    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    private lateinit var command: OrganizationDeleteCommand

    init {
        DataTableType(::organizationDeleteParams)

        When("I delete an organization") {
            step {
                deleteOrganization(organizationDeleteParams(null))
            }
        }

        When("I delete an organization:") { params: OrganizationDeleteParams ->
            step {
                deleteOrganization(params)
            }
        }

        Given("An organization is deleted") {
            step {
                deleteOrganization(organizationDeleteParams(null))
            }
        }

        Given("An organization is deleted:") { params: OrganizationDeleteParams ->
            step {
                deleteOrganization(params)
            }
        }

        Given("Some organizations are deleted:") { dataTable: DataTable ->
            step {
                dataTable.asList(OrganizationDeleteParams::class.java)
                    .forEach { deleteOrganization(it) }
            }
        }

        Then("The organization should be deleted") {
            step {
                val client = keycloakClientProvider.getClient()
                AssertionBdd.organization(client).notExists(command.id)
            }
        }
    }

    private suspend fun deleteOrganization(params: OrganizationDeleteParams) {
        command = OrganizationCoreDeleteCommand(
            id = context.organizationIds.safeGet(params.identifier)
        )
        organizationEndpoint.organizationDelete().invoke(command).id
    }

    private fun organizationDeleteParams(entry: Map<String, String>?): OrganizationDeleteParams {
        return OrganizationDeleteParams(
            identifier = entry?.get("identifier") ?: context.organizationIds.lastUsedKey
        )
    }

    private data class OrganizationDeleteParams(
        val identifier: TestContextKey
    )
}
