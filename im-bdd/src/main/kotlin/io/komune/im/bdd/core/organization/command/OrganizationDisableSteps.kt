package io.komune.im.bdd.core.organization.command

import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.organization.data.organization
import io.komune.im.bdd.core.user.data.user
import io.komune.im.f2.organization.api.OrganizationEndpoint
import io.komune.im.f2.organization.domain.command.OrganizationDisableCommand
import io.komune.im.f2.organization.domain.command.OrganizationDisabledEvent
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey

class OrganizationDisableSteps: En, ImCucumberStepsDefinition() {
    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    private lateinit var command: OrganizationDisableCommand
    private lateinit var event: OrganizationDisabledEvent

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
                dataTable.asList(OrganizationDisableParams::class.java).filterNotNull()
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

        Then("The organization disable should have disabled only the users:") { dataTable: DataTable ->
            step {
                val rows = dataTable.asMaps()
                Assertions.assertThat(event.userIds).containsExactlyInAnyOrderElementsOf(
                    rows.map { context.userIds.safeGet(requireNotNull(it["identifier"])) }
                )
            }
        }

        Then("The users should be disabled by:") { dataTable: DataTable ->
            step {
                val client = keycloakClientProvider.getClient()
                dataTable.asMaps().forEach { row ->
                    AssertionBdd.user(client)
                        .assertThatId(context.userIds.safeGet(requireNotNull(row["identifier"])))
                        .hasFields(enabled = false, disabledBy = row["disabledBy"])
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
        event = organizationEndpoint.organizationDisable().invoke(command)
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
