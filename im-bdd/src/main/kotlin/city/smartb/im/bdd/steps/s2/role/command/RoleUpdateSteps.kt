//package city.smartb.im.bdd.steps.s2.role.command
//
//import city.smartb.im.bdd.CucumberStepsDefinition
//import city.smartb.im.bdd.assertion.AssertionBdd
//import city.smartb.im.bdd.data.TestContextKey
//import city.smartb.im.bdd.data.parser.extractList
//import city.smartb.im.bdd.steps.s2.role.assertion.role
//import city.smartb.im.privilege.api.service.PrivilegeAggregateService
//import city.smartb.im.privilege.domain.role.command.RoleUpdateCommand
//import io.cucumber.datatable.DataTable
//import io.cucumber.java8.En
//import org.springframework.beans.factory.annotation.Autowired
//
//class RoleUpdateSteps: En, CucumberStepsDefinition() {
//
//    @Autowired
//    private lateinit var privilegeAggregateService: PrivilegeAggregateService
//
//    @Autowired
//    private lateinit var roleQueryApi: RoleQueryApi
//
//    private lateinit var command: RoleUpdateCommand
//
//    init {
//        DataTableType(::roleUpdateParams)
//
//        When("I update the role") {
//            step {
//                updateRole(roleUpdateParams(null))
//            }
//        }
//
//        When("I update the role:") { params: RoleUpdateParams ->
//            step {
//                updateRole(params)
//            }
//        }
//
//        Given("The role is updated") {
//            step {
//                updateRole(roleUpdateParams(null))
//            }
//        }
//
//        Given("The role is updated:") { params: RoleUpdateParams ->
//            step {
//                updateRole(params)
//            }
//        }
//
//        Given("The roles are updated:") { dataTable: DataTable ->
//            step {
//                dataTable.asList(RoleUpdateParams::class.java)
//                    .forEach { updateRole(it) }
//            }
//        }
//
//        Then("The role should be updated") {
//            step {
//                val roleId = context.roleIdentifiers.lastUsed
//                AssertionBdd.role(roleQueryApi).assertThat(roleId).hasFields(
//                    name = command.name,
//                    description = command.description,
//                    isClientRole = command.isClientRole,
//                )
//            }
//        }
//    }
//
//    private suspend fun updateRole(params: RoleUpdateParams) {
//        command = RoleUpdateCommand(
//            name = params.name,
//            composites = params.composites,
//            description = params.description,
//            isClientRole = params.isClientRole
//        )
//        privilegeAggregateService.roleUpdate(command).id
//    }
//
//    private fun roleUpdateParams(entry: Map<String, String>?) = RoleUpdateParams(
//        identifier = entry?.get("identifier") ?: context.roleIdentifiers.lastUsedKey,
//        name = entry?.get("name") ?: "roleName-${context.roleIdentifiers.lastUsedKey}",
//        description = entry?.get("description") ?: "6666",
//        isClientRole = entry?.get("isClientRole").toBoolean(),
//        composites = entry?.extractList("composites") ?: emptyList()
//    )
//
//    private data class RoleUpdateParams(
//        val identifier: TestContextKey,
//        val name: String,
//        val description: String?,
//        val composites: List<String>,
//        val isClientRole: Boolean
//    )
//}
