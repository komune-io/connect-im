package city.smartb.im.bdd.steps.s2.user.command

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.bdd.steps.s2.user.assertion.user
import city.smartb.im.user.api.UserEndpoint
import city.smartb.im.user.domain.features.command.UserDisableCommand
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired

class UserDisableSteps: En, CucumberStepsDefinition() {
    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    private lateinit var command: UserDisableCommand

    init {
        DataTableType(::userDisableParams)

        When("I disable a user") {
            step {
                disableUser(userDisableParams(null))
            }
        }

        When("I disable a user:") { params: UserDisableParams ->
            step {
                disableUser(params)
            }
        }

        Given("A user is disabled") {
            step {
                disableUser(userDisableParams(null))
            }
        }

        Given("A user is disabled:") { params: UserDisableParams ->
            step {
                disableUser(params)
            }
        }

        Given("Some users are disabled:") { dataTable: DataTable ->
            step {
                dataTable.asList(UserDisableParams::class.java)
                    .forEach { disableUser(it) }
            }
        }

        Then("The user should be disabled") {
            step {
                AssertionBdd.user(userEndpoint).assertThat(command.id).hasFields(
                    enabled = false
                )
            }
        }
    }

    private fun disableUser(params: UserDisableParams) = runBlocking {
        command = UserDisableCommand(
            id = context.userIds.safeGet(params.identifier)
        )
        userEndpoint.userDisable().invoke(command).id
    }

    private fun userDisableParams(entry: Map<String, String>?): UserDisableParams {
        return UserDisableParams(
            identifier = entry?.get("identifier") ?: context.userIds.lastUsedKey
        )
    }

    private data class UserDisableParams(
        val identifier: TestContextKey
    )
}
