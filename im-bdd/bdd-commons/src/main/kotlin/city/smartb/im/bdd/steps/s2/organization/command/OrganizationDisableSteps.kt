package city.smartb.im.bdd.steps.s2.organization.command

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.bdd.steps.s2.organization.assertion.organization
import city.smartb.im.organization.api.OrganizationEndpoint
import city.smartb.im.organization.domain.features.command.OrganizationDisableCommand
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired

class OrganizationDisableSteps: En, CucumberStepsDefinition() {
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
                val assertThat = AssertionBdd.organization(organizationEndpoint).assertThat(organizationId)

                assertThat.hasFields(enabled = false)
                if (command.anonymize) {
                   assertThat.isAnonymized()
                }
            }
        }
    }

    private fun disableOrganization(params: OrganizationDisableParams) = runBlocking {
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
