package io.komune.im.bdd.core.user.command

import f2.dsl.fnc.invoke
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.user.data.user
import io.komune.im.f2.user.api.UserEndpoint
import io.komune.im.f2.user.domain.command.UserUpdateEmailCommand
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey

class UserUpdateEmailSteps: En, ImCucumberStepsDefinition() {
    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    private lateinit var command: UserUpdateEmailCommand

    init {
        DataTableType(::userUpdateEmailParams)

        When("I update the email of a user") {
            step {
                updateEmailUser(userUpdateEmailParams(null))
            }
        }

        When("I update the email of a user:") { params: UserUpdateEmailParams ->
            step {
                updateEmailUser(params)
            }
        }

        Given("A user's email is updated") {
            step {
                updateEmailUser(userUpdateEmailParams(null))
            }
        }

        Given("A user's email is updated:") { params: UserUpdateEmailParams ->
            step {
                updateEmailUser(params)
            }
        }

        Then("The user's email should be updated") {
            step {
                val userId = context.userIds.lastUsed

                AssertionBdd.user(keycloakClient()).assertThatId(userId).hasFields(
                    email = command.email
                )
            }
        }
    }

    private suspend fun updateEmailUser(params: UserUpdateEmailParams) {
        command = UserUpdateEmailCommand(
            id = context.userIds.safeGet(params.identifier),
            email = params.email
        )

        userEndpoint.userUpdateEmail().invoke(command).id
    }

    private fun userUpdateEmailParams(entry: Map<String, String>?): UserUpdateEmailParams {
        return UserUpdateEmailParams(
            identifier = entry?.get("identifier") ?: context.userIds.lastUsedKey,
            email = entry?.get("identifier") ?: "newemail@test.com"
        )
    }

    private data class UserUpdateEmailParams(
        val identifier: TestContextKey,
        val email: String
    )
}
