package city.smartb.im.bdd.steps.s2.user.finder

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.user.api.UserEndpoint
import city.smartb.im.user.domain.features.query.UserGetQuery
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class UserGetSteps: En, CucumberStepsDefinition() {

    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    init {
        DataTableType(::userGetParams)

        When ("I get a user by ID") {
            step {
                userGet(userGetParams(null))
            }
        }

        When ("I get a user by ID:") { params: UserGetParams ->
            step {
                userGet(params)
            }
        }
    }

    private suspend fun userGet(params: UserGetParams) {
        context.fetched.users = listOfNotNull(
            UserGetQuery(
                id = params.identifier
            )
                .invokeWith(userEndpoint.userGet())
                .item
        )
    }

    private fun userGetParams(entry: Map<String, String>?) = UserGetParams(
        identifier = entry?.get("identifier") ?: context.userIds.lastUsed
    )

    private data class UserGetParams(
        val identifier: TestContextKey
    )
}
