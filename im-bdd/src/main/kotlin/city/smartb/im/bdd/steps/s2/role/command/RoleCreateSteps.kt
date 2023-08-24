package city.smartb.im.bdd.steps.s2.role.command

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.bdd.data.parser.extractList
import city.smartb.im.privilege.domain.RoleQueryApi
import city.smartb.im.privilege.domain.role.command.RoleDefineCommand
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

class RoleCreateSteps: En, CucumberStepsDefinition() {

//    @Autowired
//    private lateinit var privilegeAggregateService: PrivilegeAggregateService

    @Autowired
    private lateinit var roleQueryApi: RoleQueryApi

    private lateinit var command: RoleDefineCommand

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
                val roleId = context.roleIdentifiers.lastUsed
//                AssertionBdd.role(roleQueryApi).assertThat(roleId).hasFields(
//                    name = command.name,
//                    description = command.description,
//                    isClientRole = command.isClientRole
//                )
                TODO()
            }
        }
    }

    private suspend fun createRole(params: RoleInitParams) = context.roleIdentifiers.register(params.identifier) {
//        command = RoleDefineCommand(
//            name = params.name,
//            description = params.description,
//            isClientRole = params.isClientRole,
//            composites = params.composites,
//        )
//        privilegeAggregateService.define(command).id
        TODO()
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
