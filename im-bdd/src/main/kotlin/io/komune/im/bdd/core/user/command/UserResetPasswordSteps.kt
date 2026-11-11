package io.komune.im.bdd.core.user.command

import f2.dsl.fnc.invoke
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.f2.user.api.UserEndpoint
import io.komune.im.f2.user.domain.command.UserResetPasswordCommand
import s2.bdd.data.TestContextKey

/** Cucumber steps: user reset password. */
class UserResetPasswordSteps(
    private val userEndpoint: UserEndpoint,
): En, ImCucumberStepsDefinition() {

    private var commandOrNull: UserResetPasswordCommand? = null
    private var command: UserResetPasswordCommand
        get() = checkNotNull(commandOrNull) { "No command has been set" }
        set(value) {
            commandOrNull = value
        }

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
        val _ = userEndpoint.userResetPassword().invoke(command)
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
