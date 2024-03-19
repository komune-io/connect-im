package io.komune.im.bdd.core.privilege.role.command

import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.privilege.role.data.extractRoleTargetList
import io.komune.im.bdd.core.privilege.role.data.role
import io.komune.im.commons.model.PermissionIdentifier
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.utils.parseJson
import io.komune.im.core.privilege.domain.model.RoleTarget
import io.komune.im.f2.privilege.api.RoleEndpoint
import io.komune.im.f2.privilege.domain.role.command.RoleDefineCommand
import f2.dsl.fnc.invokeWith
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.parser.extractList
import java.util.UUID

class RoleDefineSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var roleEndpoint: RoleEndpoint

    private lateinit var command: RoleDefineCommand

    init {
        DataTableType(::roleDefineParams)

        When("I define a/the role") {
            step {
                defineRole(roleDefineParams(null))
            }
        }

        When("I define a/the role:") { params: RoleDefineParams ->
            step {
                defineRole(params)
            }
        }

        Given("A/The role is defined") {
            step {
                defineRole(roleDefineParams(null))
            }
        }

        Given("A/The role is defined:") { params: RoleDefineParams ->
            step {
                defineRole(params)
            }
        }

        Given("Some/The roles are defined:") { dataTable: DataTable ->
            step {
                dataTable.asList(RoleDefineParams::class.java)
                    .forEach { defineRole(it) }
            }
        }

        Then("The role should be defined") {
            step {
                AssertionBdd.role(keycloakClientProvider.get()).assertThatId(command.identifier).hasFields(
                    identifier = command.identifier,
                    description = command.description,
                    targets = command.targets.map { RoleTarget.valueOf(it) },
                    locale = command.locale,
                    bindings = command.bindings.orEmpty().mapKeys { (target) -> RoleTarget.valueOf(target) },
                    permissions = command.permissions.orEmpty()
                )
            }
        }
    }

    private suspend fun defineRole(params: RoleDefineParams) = context.roleIdentifiers.register(params.identifier) {
        command = RoleDefineCommand(
            identifier = params.identifier,
            description = params.description,
            targets = params.targets.map { it.name },
            locale = params.locale,
            bindings = params.bindings?.mapKeys { (target) -> target.name },
            permissions = params.permissions
        )
        command.invokeWith(roleEndpoint.roleDefine()).identifier
    }

    private fun roleDefineParams(entry: Map<String, String>?): RoleDefineParams {
        return RoleDefineParams(
            identifier = entry?.get("identifier") ?: context.roleIdentifiers.lastUsedOrNull.orRandom(),
            description = entry?.get("description") ?: UUID.randomUUID().toString(),
            targets = entry?.extractRoleTargetList("targets").orEmpty(),
            locale = entry?.get("locale")?.parseJson() ?: emptyMap(),
            bindings = entry?.get("bindings")
                ?.parseJson<Map<String, List<RoleIdentifier>>>()
                ?.mapKeys { (key) -> RoleTarget.valueOf(key) },
            permissions = entry?.extractList("permissions")
        )
    }

    private data class RoleDefineParams(
        val identifier: RoleIdentifier,
        val description: String,
        val targets: List<RoleTarget>,
        val locale: Map<String, String>,
        val bindings: Map<RoleTarget, List<RoleIdentifier>>?,
        val permissions: List<PermissionIdentifier>?
    )
}
