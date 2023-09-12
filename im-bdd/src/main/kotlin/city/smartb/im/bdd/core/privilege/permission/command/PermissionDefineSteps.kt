package city.smartb.im.bdd.core.privilege.permission.command

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.bdd.core.privilege.permission.data.permission
import city.smartb.im.commons.model.PermissionIdentifier
import city.smartb.im.f2.privilege.api.PermissionEndpoint
import city.smartb.im.f2.privilege.domain.permission.command.PermissionDefineCommand
import f2.dsl.fnc.invokeWith
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import java.util.UUID

class PermissionDefineSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var permissionEndpoint: PermissionEndpoint

    private lateinit var command: PermissionDefineCommand

    init {
        DataTableType(::permissionDefineParams)

        When("I define a/the permission") {
            step {
                definePermission(permissionDefineParams(null))
            }
        }

        When("I define a/the permission:") { params: PermissionDefineParams ->
            step {
                definePermission(params)
            }
        }

        Given("A/The permission is defined") {
            step {
                definePermission(permissionDefineParams(null))
            }
        }

        Given("A/The permission is defined:") { params: PermissionDefineParams ->
            step {
                definePermission(params)
            }
        }

        Given("Some/The permissions are defined:") { dataTable: DataTable ->
            step {
                dataTable.asList(PermissionDefineParams::class.java)
                    .forEach { definePermission(it) }
            }
        }

        Then("The permission should be defined") {
            step {
                AssertionBdd.permission(keycloakClientProvider.get()).assertThatId(command.identifier).hasFields(
                    identifier = command.identifier,
                    description = command.description,
                )
            }
        }
    }

    private suspend fun definePermission(params: PermissionDefineParams) = context.permissionIdentifiers.register(params.identifier) {
        command = PermissionDefineCommand(
            identifier = params.identifier,
            description = params.description,
        )
        command.invokeWith(permissionEndpoint.permissionDefine()).identifier
    }

    private fun permissionDefineParams(entry: Map<String, String>?): PermissionDefineParams {
        return PermissionDefineParams(
            identifier = entry?.get("identifier") ?: context.permissionIdentifiers.lastUsedOrNull.orRandom(),
            description = entry?.get("description") ?: UUID.randomUUID().toString(),
        )
    }

    private data class PermissionDefineParams(
        val identifier: PermissionIdentifier,
        val description: String,
    )
}
