package city.smartb.im.bdd.core.user.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.bdd.core.user.data.user
import city.smartb.im.commons.utils.mapAsync
import city.smartb.im.f2.user.api.UserEndpoint
import city.smartb.im.f2.user.domain.model.User
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class UserFinderSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    init {
        DataTableType { entry: Map<String, String> ->
            FetchByIdParams(
                identifier = entry.safeExtract("identifier")
            )
        }

        DataTableType { entry: Map<String, String> ->
            UserFetchedParams(
                identifier = entry.safeExtract("identifier")
            )
        }

        Then("I should receive the user") {
            step {
                assertUsersFetched(listOf(context.userIds.lastUsedKey))
            }
        }

        Then("I should receive null instead of a user") {
            step {
                assertUsersFetched(emptyList())
            }
        }

        Then("I should receive a list of users:") { dataTable: DataTable ->
            step {
                dataTable.asList(UserFetchedParams::class.java)
                    .map(UserFetchedParams::identifier)
                    .let { assertUsersFetched(it) }
            }
        }
    }

    private suspend fun assertUsersFetched(identifiers: List<TestContextKey>) {
        val fetchedIds = context.fetched.users.map(User::id)
        val expectedIds = identifiers.map(context.userIds::safeGet).toTypedArray()
        Assertions.assertThat(fetchedIds).containsExactlyInAnyOrder(*expectedIds)

        val userAsserter = AssertionBdd.user(keycloakClient())
        context.fetched.users.mapAsync { user ->
            userAsserter.assertThatId(user.id).matches(user)
        }
    }

    private data class FetchByIdParams(
        val identifier: TestContextKey
    )

    private data class UserFetchedParams(
        val identifier: TestContextKey
    )
}
