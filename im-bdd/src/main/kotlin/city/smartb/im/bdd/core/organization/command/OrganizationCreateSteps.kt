package city.smartb.im.bdd.core.organization.command

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.bdd.core.organization.data.extractOrganizationStatus
import city.smartb.im.bdd.core.organization.data.organization
import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.f2.organization.api.OrganizationEndpoint
import city.smartb.im.f2.organization.domain.command.OrganizationCreateCommandDTOBase
import city.smartb.im.f2.organization.domain.model.OrganizationStatus
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import java.util.UUID

class OrganizationCreateSteps: En, ImCucumberStepsDefinition() {
    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    private lateinit var command: OrganizationCreateCommandDTOBase

    init {
        DataTableType(::organizationInitParams)

        When("I create an organization") {
            step {
                createOrganization(organizationInitParams(null))
            }
        }

        When("I create an organization:") { params: OrganizationInitParams ->
            step {
                createOrganization(params)
            }
        }

        Given("An organization is created") {
            step {
                createOrganization(organizationInitParams(null))
            }
        }

        Given("An organization is created:") { params: OrganizationInitParams ->
            step {
                createOrganization(params)
            }
        }

        Given("Some organizations are created:") { dataTable: DataTable ->
            step {
                dataTable.asList(OrganizationInitParams::class.java)
                    .forEach { createOrganization(it) }
            }
        }

        Then("The organization should be created") {
            step {
                val organizationId = context.organizationIds.lastUsed
                AssertionBdd.organization(keycloakClient()).assertThatId(organizationId).hasFields(
                    siret = command.siret,
                    name = command.name,
                    description = command.description,
                    address = command.address,
                    website = command.website,
                    roles = command.roles,
                    attributes = command.attributes ?: emptyMap(),
                    status = command.status
                )
            }
        }
    }

    private suspend fun createOrganization(params: OrganizationInitParams) = context.organizationIds.register(params.identifier) {
        command = OrganizationCreateCommandDTOBase(
            siret = params.siret,
            name = params.name,
            description = params.description,
            address = params.address,
            website = params.website,
            roles = params.roles,
            parentOrganizationId = params.parentOrganizationId,
            attributes = params.attributes,
            status = params.status.name
        )
        organizationEndpoint.organizationCreate().invoke(command).id
    }

    private fun organizationInitParams(entry: Map<String, String>?): OrganizationInitParams {
        val identifier = entry?.get("identifier").orRandom()
        return OrganizationInitParams(
            identifier = identifier,
            siret = entry?.get("siret") ?: "12345678912345",
            name = entry?.get("name") ?: "organizationName-${identifier}",
            description = entry?.get("description") ?: UUID.randomUUID().toString(),
            address = Address(
                street = "street",
                postalCode = "12345",
                city = "city"
            ),
            website = entry?.get("website") ?: "https://smartb.network",
            roles = listOfNotNull(context.roleIdentifiers.lastUsedOrNull),
            parentOrganizationId = entry?.get("parentOrganizationId"),
            attributes = organizationAttributesParams(entry),
            status = entry?.extractOrganizationStatus("status") ?: OrganizationStatus.VALIDATED
        )
    }

    private data class OrganizationInitParams(
        val identifier: TestContextKey,
        val siret: String?,
        val name: String,
        val description: String?,
        val address: Address?,
        val website: String?,
        val roles: List<String>?,
        val parentOrganizationId: OrganizationId?,
        val attributes: Map<String, String>,
        val status: OrganizationStatus
    )

    private fun organizationAttributesParams(entry: Map<String, String>?): Map<String, String> {
        val job = entry?.get("job") ?: "job-${UUID.randomUUID()}"
        return mapOf(
            "job" to job
        )
    }
}
