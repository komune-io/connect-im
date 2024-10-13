package io.komune.im.bdd.core.privilege.role.query

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.privilege.role.data.role
import io.komune.im.f2.privilege.domain.role.model.Role
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class RoleQuerySteps: En, ImCucumberStepsDefinition() {
    init {
        DataTableType(::roleFetchedParams)

        Then("I should receive the role") {
            step {
                assertFetchedRoles(listOf(context.roleIdentifiers.lastUsedKey))
            }
        }

        Then("I should receive null instead of a role") {
            step {
                assertFetchedRoles(emptyList())
            }
        }

        Then("I should receive an empty list of roles") {
            step {
                assertFetchedRoles(emptyList())
            }
        }

        Then("I should receive a list of roles:") { dataTable: DataTable ->
            step {
                dataTable.asList(RoleFetchedParams::class.java)
                    .map(RoleFetchedParams::identifier)
                    .let { assertFetchedRoles(it) }
            }
        }
    }

    private suspend fun assertFetchedRoles(identifiers: List<TestContextKey>) = coroutineScope {
        val fetchedRoles = context.fetched.roles.filter { it.identifier !in context.permanentRoles() }
        val fetchedIdentifiers = fetchedRoles.map(Role::identifier)
        val expectedIdentifiers = identifiers.map { context.roleIdentifiers[it] ?: it }
        Assertions.assertThat(fetchedIdentifiers).containsExactlyInAnyOrderElementsOf(expectedIdentifiers)

        val roleAsserter = AssertionBdd.role(keycloakClientProvider.get())
        fetchedRoles.map { role ->
            async { roleAsserter.assertThatId(role.identifier).matches(role) }
        }.awaitAll()
    }

    private fun roleFetchedParams(entry: Map<String, String>) = RoleFetchedParams(
        identifier = entry.safeExtract("identifier")
    )

    private data class RoleFetchedParams(
        val identifier: TestContextKey
    )
}
