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
                val assertThat = AssertionBdd.user(userEndpoint).assertThat(command.id)
                assertThat.hasFields(
                    enabled = false,
                    disabledBy = command.disabledBy,
                )

                if (command.anonymize) {
                    assertThat.isAnonymized()
                }
            }
        }
    }

    private fun disableUser(params: UserDisableParams) = runBlocking {
        command = UserDisableCommand(
            id = context.userIds.safeGet(params.identifier),
            disabledBy = params.disabledBy,
            anonymize = params.anonymize,
            attributes = null
        )
        userEndpoint.userDisable().invoke(command).id
    }

    private fun userDisableParams(entry: Map<String, String>?): UserDisableParams {
        return UserDisableParams(
            identifier = entry?.get("identifier") ?: context.userIds.lastUsedKey,
            disabledBy = entry?.get("disabledBy"),
            anonymize = entry?.get("anonymize").toBoolean()
        )
    }

    private data class UserDisableParams(
        val identifier: TestContextKey,
        val disabledBy: String?,
        val anonymize: Boolean
    )
}
