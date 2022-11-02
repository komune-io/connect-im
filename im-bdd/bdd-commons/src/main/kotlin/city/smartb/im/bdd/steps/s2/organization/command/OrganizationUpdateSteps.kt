package city.smartb.im.bdd.steps.s2.organization.command

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.bdd.steps.s2.organization.assertion.organization
import city.smartb.im.commons.model.Address
import city.smartb.im.organization.api.OrganizationEndpoint
import city.smartb.im.organization.domain.features.command.OrganizationUpdateCommand
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired

class OrganizationUpdateSteps: En, CucumberStepsDefinition() {
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

                AssertionBdd.organization(organizationEndpoint).assertThat(organizationId).hasFields(
                    name = command.name,
                    description = command.description ?: organization.description,
                    address = command.address ?: organization.address,
                    website = command.website ?: organization.website,
                    roles = command.roles ?: organization.roles?.effectiveRoles,
                    attributes = command.attributes ?: organization.attributes
                )
            }
        }
    }

    private fun updateOrganization(params: OrganizationUpdateParams) = runBlocking {
        command = OrganizationUpdateCommand(
            id = context.organizationIds.safeGet(params.identifier),
            name = params.name,
            description = params.description,
            address = params.address,
            website = params.website,
            roles = params.roles,
            attributes = params.attributes
        )
        organizationEndpoint.organizationUpdate().invoke(command).id
    }

    private fun organizationUpdateParams(entry: Map<String, String>?): OrganizationUpdateParams = runBlocking {
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
            roles = listOfNotNull(context.roleIds.lastUsedOrNull),
            attributes = organizationAttributesParams(entry)
        )
    }

    private data class OrganizationUpdateParams(
        val identifier: TestContextKey,
        val name: String,
        val description: String?,
        val address: Address?,
        val website: String?,
        val roles: List<String>?,
        val attributes: Map<String, String>?
    )


    private fun organizationAttributesParams(entry: Map<String, String>?): Map<String, String>? {
        if (entry == null) return null
        if (entry.containsKey("job")) {
            return mapOf(
                "job" to entry["job"]!!
            )
        }
        return null
    }
}
