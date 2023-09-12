package city.smartb.im.bdd.core.user.command

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.bdd.core.user.data.user
import city.smartb.im.core.user.domain.command.UserCoreDeleteCommand
import city.smartb.im.f2.user.api.UserEndpoint
import city.smartb.im.f2.user.domain.command.UserDeleteCommand
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey

class UserDeleteSteps: En, ImCucumberStepsDefinition() {
    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    private lateinit var command: UserDeleteCommand

    init {
        DataTableType(::userDeleteParams)

        When("I delete a user") {
            step {
                deleteUser(userDeleteParams(null))
            }
        }

        When("I delete a user:") { params: UserDeleteParams ->
            step {
                deleteUser(params)
            }
        }

        Given("A user is deleted") {
            step {
                deleteUser(userDeleteParams(null))
            }
        }

        Given("A user is deleted:") { params: UserDeleteParams ->
            step {
                deleteUser(params)
            }
        }

        Given("Some users are deleted:") { dataTable: DataTable ->
            step {
                dataTable.asList(UserDeleteParams::class.java)
                    .forEach { deleteUser(it) }
            }
        }

        Then("The user should be deleted") {
            step {
                AssertionBdd.user(keycloakClient()).notExists(command.id)
            }
        }
    }

    private suspend fun deleteUser(params: UserDeleteParams) {
        command = UserCoreDeleteCommand(
            id = context.userIds.safeGet(params.identifier)
        )
        userEndpoint.userDelete().invoke(command).id
    }

    private fun userDeleteParams(entry: Map<String, String>?): UserDeleteParams {
        return UserDeleteParams(
            identifier = entry?.get("identifier") ?: context.userIds.lastUsedKey
        )
    }

    private data class UserDeleteParams(
        val identifier: TestContextKey
    )
}
