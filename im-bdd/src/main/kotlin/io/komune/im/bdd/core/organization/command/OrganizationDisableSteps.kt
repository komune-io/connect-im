package io.komune.im.bdd.core.organization.command

import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.organization.data.organization
import io.komune.im.f2.organization.api.OrganizationEndpoint
import io.komune.im.f2.organization.domain.command.OrganizationDisableCommand
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey

class OrganizationDisableSteps: En, ImCucumberStepsDefinition() {
    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    private lateinit var command: OrganizationDisableCommand

    init {
        DataTableType(::organizationDisableParams)

        When("I disable an organization") {
            step {
                disableOrganization(organizationDisableParams(null))
            }
        }

        When("I disable an organization:") { params: OrganizationDisableParams ->
            step {
                disableOrganization(params)
            }
        }

        Given("An organization is disabled") {
            step {
                disableOrganization(organizationDisableParams(null))
            }
        }

        Given("An organization is disabled:") { params: OrganizationDisableParams ->
            step {
                disableOrganization(params)
            }
        }

        Given("Some organizations are disabled:") { dataTable: DataTable ->
            step {
                dataTable.asList(OrganizationDisableParams::class.java)
                    .forEach { disableOrganization(it) }
            }
        }

        Then("The organization should be disabled") {
            step {
                val organizationId = context.organizationIds.lastUsed
                val client = keycloakClientProvider.getClient()
                val assertThat = AssertionBdd.organization(client).assertThatId(organizationId)

                assertThat.hasFields(enabled = false)
                if (command.anonymize) {
                   assertThat.isAnonym()
                }
            }
        }
    }

    private suspend fun disableOrganization(params: OrganizationDisableParams) {
        command = OrganizationDisableCommand(
            id = context.organizationIds.safeGet(params.identifier),
            disabledBy = params.disabledBy,
            anonymize = params.anonymize,
            attributes = null,
            userAttributes = null
        )
        organizationEndpoint.organizationDisable().invoke(command).id
    }

    private fun organizationDisableParams(entry: Map<String, String>?): OrganizationDisableParams {
        return OrganizationDisableParams(
            identifier = entry?.get("identifier") ?: context.organizationIds.lastUsedKey,
            disabledBy = entry?.get("disabledBy"),
            anonymize = entry?.get("anonymize").toBoolean()
        )
    }

    private data class OrganizationDisableParams(
        val identifier: TestContextKey,
        val disabledBy: String?,
        val anonymize: Boolean
    )
}
