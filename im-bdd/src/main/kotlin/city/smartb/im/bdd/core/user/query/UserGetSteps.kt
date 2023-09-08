package city.smartb.im.bdd.core.user.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.f2.user.api.UserEndpoint
import city.smartb.im.f2.user.domain.query.UserGetQuery
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.TestContextKey

class UserGetSteps: En, ImCucumberStepsDefinition() {

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
