package city.smartb.im.bdd.steps.s2.user.command

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.bdd.steps.s2.user.assertion.user
import city.smartb.im.user.api.UserEndpoint
import city.smartb.im.user.domain.features.command.UserDeleteCommand
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired

class UserDeleteSteps: En, CucumberStepsDefinition() {
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
                AssertionBdd.user(userEndpoint).notExists(command.id)
            }
        }
    }

    private fun deleteUser(params: UserDeleteParams) = runBlocking {
        command = UserDeleteCommand(
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
