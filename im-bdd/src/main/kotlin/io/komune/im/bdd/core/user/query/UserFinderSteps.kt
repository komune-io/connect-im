package io.komune.im.bdd.core.user.query

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.user.data.user
import io.komune.im.commons.utils.mapAsync
import io.komune.im.f2.user.domain.model.User
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class UserFinderSteps: En, ImCucumberStepsDefinition() {

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

        val client = keycloakClientProvider.getClient()
        val userAsserter = AssertionBdd.user(client)
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
