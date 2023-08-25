package city.smartb.im.bdd

import city.smartb.im.commons.auth.Roles
import io.cucumber.java8.En
import s2.bdd.auth.AuthedUser
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class AuthenticationSteps: En, ImCucumberStepsDefinition() {

    init {
        DataTableType(::authenticationParams)

        Given("I am authenticated as:") { params: AuthenticationParams ->
            step {
//                context.authedUser = context.users.safeGet(params.identifier)
                TODO()
            }
        }

        Given("I am authenticated as admin") {
            step {
                context.authedUser = AuthedUser(
                    id = "admin",
                    roles = arrayOf(Roles.SUPER_ADMIN),
                    memberOf = null
                )
            }
        }

        Given("I am not authenticated") {
            step {
                context.authedUser = null
            }
        }
    }

    private fun authenticationParams(entry: Map<String, String>) = AuthenticationParams(
        identifier = entry.safeExtract("identifier")
    )

    private data class AuthenticationParams(
        val identifier: TestContextKey
    )
}
