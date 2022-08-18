package city.smartb.im.bdd.steps.s2.user.command

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.user.api.UserEndpoint
import city.smartb.im.user.domain.features.command.UserResetPasswordCommand
import f2.dsl.fnc.invoke
import io.cucumber.java8.En
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired

class UserResetPasswordSteps: En, CucumberStepsDefinition() {
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

    private fun resetUserPassword(params: UserResetPasswordParams) = runBlocking {
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
