package io.komune.im.bdd.core.organization.command

import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.organization.data.organization
import io.komune.im.commons.model.Address
import io.komune.im.f2.organization.api.OrganizationEndpoint
import io.komune.im.f2.organization.domain.command.OrganizationUpdateCommand
import io.komune.im.f2.organization.domain.model.OrganizationStatus
import io.komune.im.f2.organization.domain.query.OrganizationGetQuery
import io.komune.im.f2.privilege.domain.role.model.Role
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.extract

class OrganizationUpdateSteps : En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    private lateinit var command: OrganizationUpdateCommand

    init {
        DataTableType(::organizationUpdateParams)

        When("I update an organization") {
            step {
                updateOrganization(organizationUpdateParams(null))
            }
        }

        When("I update an organization:") { params: OrganizationUpdateParams ->
            step {
                updateOrganization(params)
            }
        }

        Given("An organization is updated") {
            step {
                updateOrganization(organizationUpdateParams(null))
            }
        }

        Given("An organization is updated:") { params: OrganizationUpdateParams ->
            step {
                updateOrganization(params)
            }
        }

        Given("Some organizations are updated:") { dataTable: DataTable ->
            step {
                dataTable.asList(OrganizationUpdateParams::class.java)
                    .forEach { updateOrganization(it) }
            }
        }

        Then("The organization should be updated") {
            step {
                val organizationId = context.organizationIds.lastUsed
                val organization = organizationEndpoint.organizationGet()
                    .invoke(OrganizationGetQuery(organizationId))
                    .item!!

                val client = keycloakClientProvider.getClient()
                AssertionBdd.organization(client).assertThatId(organizationId).hasFields(
                    name = command.name,
                    description = command.description ?: organization.description,
                    address = command.address ?: organization.address,
                    website = command.website ?: organization.website,
                    roles = command.roles ?: organization.roles.map(Role::identifier),
                    attributes = command.attributes ?: organization.attributes,
                    status = command.status
                )
            }
        }
    }

    private suspend fun updateOrganization(params: OrganizationUpdateParams) {
        command = OrganizationUpdateCommand(
            id = context.organizationIds.safeGet(params.identifier),
            name = params.name,
            description = params.description,
            address = params.address,
            website = params.website,
            roles = params.roles,
            attributes = params.attributes,
            status = params.status.name
        )
        organizationEndpoint.organizationUpdate().invoke(command).id
    }

    private fun organizationUpdateParams(
        entry: Map<String, String>?
    ): OrganizationUpdateParams = runBlocking(authedContext()) {
        val identifier = entry?.get("identifier") ?: context.organizationIds.lastUsedKey
        val organization = organizationEndpoint.organizationGet()
            .invoke(OrganizationGetQuery(context.organizationIds.safeGet(identifier)))
            .item!!

        OrganizationUpdateParams(
            identifier = identifier,
            name = entry?.get("name") ?: organization.name,
            description = entry?.get("description"),
            address = Address(
                street = entry?.get("street") ?: organization.address!!.street,
                city = entry?.get("city") ?: organization.address!!.city,
                postalCode = entry?.get("postalCode") ?: organization.address!!.postalCode,
            ),
            website = entry?.get("website"),
            roles = listOfNotNull(context.roleIdentifiers.lastUsedOrNull),
            attributes = organizationAttributesParams(entry),
            status = entry?.extract<OrganizationStatus>("status") ?: OrganizationStatus.VALIDATED
        )
    }

    private data class OrganizationUpdateParams(
        val identifier: TestContextKey,
        val name: String,
        val description: String?,
        val address: Address?,
        val website: String?,
        val roles: List<String>?,
        val attributes: Map<String, String>?,
        val status: OrganizationStatus
    )

    private fun organizationAttributesParams(entry: Map<String, String>?): Map<String, String>? {
        if (entry == null) return null
        val job = entry["job"]
        return if (job != null) {
             mapOf(
                "job" to job
            )
        } else null
    }
}
