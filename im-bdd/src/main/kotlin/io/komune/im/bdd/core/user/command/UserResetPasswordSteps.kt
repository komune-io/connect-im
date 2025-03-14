package io.komune.im.bdd.core.user.command

import f2.dsl.fnc.invoke
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.f2.user.api.UserEndpoint
import io.komune.im.f2.user.domain.command.UserResetPasswordCommand
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.TestContextKey

class UserResetPasswordSteps: En, ImCucumberStepsDefinition() {
    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    private lateinit var command: UserResetPasswordCommand

    init {
        DataTableType(::userResetPasswordParams)

        When("I reset the password of a user") {
            step {
                resetUserPassword(userResetPasswordParams(null))
            }
        }

        When("I reset the password of a user:") { params: UserResetPasswordParams ->
            step {
                resetUserPassword(params)
            }
        }

        Given("A user's password is reset") {
            step {
                resetUserPassword(userResetPasswordParams(null))
            }
        }

        Given("A user's password is reset:") { params: UserResetPasswordParams ->
            step {
                resetUserPassword(params)
            }
        }
    }

    private suspend fun resetUserPassword(params: UserResetPasswordParams) {
        command = UserResetPasswordCommand(
            id = context.userIds.safeGet(params.identifier)
        )
        userEndpoint.userResetPassword().invoke(command).id
    }

    private fun userResetPasswordParams(entry: Map<String, String>?): UserResetPasswordParams {
        return UserResetPasswordParams(
            identifier = entry?.get("identifier") ?: context.userIds.lastUsedKey
        )
    }

    private data class UserResetPasswordParams(
        val identifier: TestContextKey
    )
}
