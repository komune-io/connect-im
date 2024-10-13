package io.komune.im.bdd.core.privilege.permission.command

import f2.dsl.fnc.invokeWith
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.privilege.permission.data.permission
import io.komune.im.commons.model.PermissionIdentifier
import io.komune.im.f2.privilege.api.PermissionEndpoint
import io.komune.im.f2.privilege.domain.permission.command.PermissionDefineCommand
import java.util.UUID
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd

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

    private suspend fun definePermission(
        params: PermissionDefineParams
    ) = context.permissionIdentifiers.register(params.identifier) {
        command = PermissionDefineCommand(
            identifier = params.identifier,
            description = params.description,
            features = null
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
