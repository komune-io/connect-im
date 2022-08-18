package city.smartb.im.bdd.steps.s2.role.command

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.bdd.data.parser.extractList
import city.smartb.im.bdd.steps.s2.role.assertion.role
import city.smartb.im.role.api.RoleQueryApi
import city.smartb.im.role.api.service.RoleAggregateService
import city.smartb.im.role.domain.features.command.RoleCreateCommand
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import java.util.UUID
import org.springframework.beans.factory.annotation.Autowired

class RoleCreateSteps: En, CucumberStepsDefinition() {

    @Autowired
    private lateinit var roleAggregateService: RoleAggregateService

    @Autowired
    private lateinit var roleQueryApi: RoleQueryApi

    private lateinit var command: RoleCreateCommand

    init {
        DataTableType(::roleInitParams)

        When("I create a role") {
            step {
                createRole(roleInitParams(null))
            }
        }

        When("I create a role:") { params: RoleInitParams ->
            step {
                createRole(params)
            }
        }

        Given("A role is created") {
            step {
                createRole(roleInitParams(null))
            }
        }

        Given("A role is created:") { params: RoleInitParams ->
            step {
                createRole(params)
            }
        }

        Given("Some roles are created:") { dataTable: DataTable ->
            step {
                dataTable.asList(RoleInitParams::class.java)
                    .forEach { createRole(it) }
            }
        }

        Then("The role should be created") {
            step {
                val roleId = context.roleIds.lastUsed
                AssertionBdd.role(roleQueryApi, context.realmId).assertThat(roleId).hasFields(
                    name = command.name,
                    description = command.description,
                    isClientRole = command.isClientRole
                )
            }
        }
    }

    private suspend fun createRole(params: RoleInitParams) = context.roleIds.register(params.identifier) {
        command = RoleCreateCommand(
            name = params.name,
            description = params.description,
            isClientRole = params.isClientRole,
            composites = params.composites,
        )
        roleAggregateService.roleCreate(command).id
    }

    private fun roleInitParams(entry: Map<String, String>?): RoleInitParams {
        val identifier = entry?.get("identifier").orRandom()
        return RoleInitParams(
            identifier = identifier,
            name = entry?.get("name") ?: "roleName-${identifier}",
            description = entry?.get("description") ?: UUID.randomUUID().toString(),
            isClientRole = entry?.get("isClientRole").toBoolean(),
            composites = entry?.extractList("composites") ?: emptyList()
        )
    }

    private data class RoleInitParams(
        val identifier: TestContextKey,
        val name: String,
        val description: String?,
        val composites: List<String>,
        val isClientRole: Boolean
    )
}
