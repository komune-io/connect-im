package city.smartb.im.bdd.steps.s2.organization.command

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.bdd.steps.s2.organization.assertion.organization
import city.smartb.im.commons.auth.OrganizationId
import city.smartb.im.commons.model.Address
import city.smartb.im.organization.api.OrganizationEndpoint
import city.smartb.im.organization.domain.features.command.OrganizationAddApiKeyCommand
import city.smartb.im.organization.domain.features.command.OrganizationCreateCommand
import city.smartb.im.organization.domain.features.command.OrganizationRemoveApiKeyCommand
import city.smartb.im.organization.domain.model.ApiKeyId
import f2.dsl.fnc.invoke
import io.cucumber.java8.En
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired

class OrganizationManageApiKeySteps: En, CucumberStepsDefinition() {

    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    private lateinit var addCommand: OrganizationAddApiKeyCommand
    private lateinit var organizationId: OrganizationId
    private lateinit var apiKeyId: ApiKeyId

    init {
        DataTableType(::organizationAddApiKeyParams)

        Given("An organization without apiKeys is created") {
            step {
                organizationId = createOrganization()
            }
        }

        When("I add an api key on the organization:") { params: OrganizationAddApiKeyParams ->
            step {
                apiKeyId = addApiKeyOrganization(params)
            }
        }

        Then("The organization ApiKeys should contains the new apiKey") {
            step {
                AssertionBdd.organization(organizationEndpoint).assertThat(organizationId).hasApiKey(addCommand.name)
            }
        }

        When("I remove the api key on the organization") {
            step {
                removeApiKeyOrganization()
            }
        }

        Then("The organization ApiKeys should not contains the apiKey") {
            step {
                AssertionBdd.organization(organizationEndpoint).assertThat(organizationId).hasNotApiKey(apiKeyId)
            }
        }
    }

    private suspend fun createOrganization(id: String = UUID.randomUUID().toString()) = runBlocking(authedContext()) {
        organizationEndpoint.organizationCreate().invoke(
            OrganizationCreateCommand(
                siret = "12345678912345",
                name = "organizationName-${id}",
                description = UUID.randomUUID().toString(),
                address = Address(
                    street = "street",
                    postalCode = "12345",
                    city = "city"
                ),
                website = "https://smartb.network",
                roles = listOfNotNull(context.roleIds.lastUsedOrNull),
                parentOrganizationId = null,
                attributes = mapOf("job" to "job-${UUID.randomUUID()}")
            )
        ).id
    }

    private suspend fun addApiKeyOrganization(params: OrganizationAddApiKeyParams) = runBlocking(authedContext()) {
        addCommand = OrganizationAddApiKeyCommand(
            id = params.id,
            name = params.name,
        )
        organizationEndpoint.organizationAddApiKey().invoke(addCommand).id
    }

    private suspend fun removeApiKeyOrganization() = runBlocking(authedContext()) {
        organizationEndpoint.organizationRemoveApiKey().invoke(
            OrganizationRemoveApiKeyCommand(
                id = organizationId,
                keyId = apiKeyId,
            )
        ).id
    }

    private fun organizationAddApiKeyParams(entry: Map<String, String>?) : OrganizationAddApiKeyParams = runBlocking(authedContext()) {
        OrganizationAddApiKeyParams(
            id = entry?.get("id") ?: organizationId,
            name = entry?.get("name") ?: "",
        )
    }

    private data class OrganizationAddApiKeyParams(
        val id: String,
        val name: String,
    )
}
