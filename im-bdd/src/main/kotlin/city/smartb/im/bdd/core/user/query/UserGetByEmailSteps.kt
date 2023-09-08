package city.smartb.im.bdd.core.user.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.f2.user.api.UserEndpoint
import city.smartb.im.f2.user.domain.query.UserGetByEmailQuery
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class UserGetByEmailSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    init {
        DataTableType(::userGetByEmailParams)

        When ("I get a user by email") {
            step {
                userGetByEmail(userGetByEmailParams(null))
            }
        }

        When ("I get a user by email:") { params: UserGetByEmailParams ->
            step {
                userGetByEmail(params)
            }
        }
    }

    private suspend fun userGetByEmail(params: UserGetByEmailParams) {
        context.fetched.users = listOfNotNull(
            UserGetByEmailQuery(
                email = params.email
            )
                .invokeWith(userEndpoint.userGetByEmail())
                .item
        )
    }

    private fun userGetByEmailParams(entry: Map<String, String>?) = UserGetByEmailParams(
        email = entry?.get("email") ?: "user@test.com"
    )

    private data class UserGetByEmailParams(
        val email: String
    )
}
