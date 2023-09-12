package city.smartb.im.bdd.core.user.command

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.bdd.core.user.data.user
import city.smartb.im.commons.auth.AuthenticationProvider
import city.smartb.im.f2.user.api.UserEndpoint
import city.smartb.im.f2.user.domain.command.UserDisableCommand
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey

class UserDisableSteps: En, ImCucumberStepsDefinition() {
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
                val assertThat = AssertionBdd.user(keycloakClient()).assertThatId(command.id)
                assertThat.hasFields(
                    enabled = false,
                    disabledBy = command.disabledBy ?: AuthenticationProvider.getAuthedUser()?.id,
                )

                if (command.anonymize) {
                    assertThat.isAnonymized()
                }
            }
        }
    }

    private suspend fun disableUser(params: UserDisableParams) {
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
