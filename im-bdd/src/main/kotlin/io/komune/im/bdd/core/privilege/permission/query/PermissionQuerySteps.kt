package io.komune.im.bdd.core.privilege.permission.query

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.privilege.permission.data.permission
import io.komune.im.commons.utils.mapAsync
import io.komune.im.f2.privilege.domain.permission.model.Permission
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class PermissionQuerySteps: En, ImCucumberStepsDefinition() {
    init {
        DataTableType(::permissionFetchedParams)

        Then("I should receive the permission") {
            step {
                assertFetchedPermissions(listOf(context.permissionIdentifiers.lastUsedKey))
            }
        }

        Then("I should receive null instead of a permission") {
            step {
                assertFetchedPermissions(emptyList())
            }
        }

        Then("I should receive an empty list of permissions") {
            step {
                assertFetchedPermissions(emptyList())
            }
        }

        Then("I should receive a list of permissions:") { dataTable: DataTable ->
            step {
                dataTable.asList(PermissionFetchedParams::class.java)
                    .map(PermissionFetchedParams::identifier)
                    .let { assertFetchedPermissions(it) }
            }
        }
    }

    private suspend fun assertFetchedPermissions(identifiers: List<TestContextKey>) {
        val fetchedPermissions = context.fetched.permissions.filter { it.identifier !in context.permanentRoles() }
        val fetchedIdentifiers = fetchedPermissions.map(Permission::identifier)
        val expectedIdentifiers = identifiers.map(context.permissionIdentifiers::safeGet)
        Assertions.assertThat(fetchedIdentifiers).containsExactlyInAnyOrderElementsOf(expectedIdentifiers)

        val permissionAsserter = AssertionBdd.permission(keycloakClientProvider.get())
        fetchedPermissions.mapAsync { permission ->
            permissionAsserter.assertThatId(permission.identifier).matches(permission)
        }
    }

    private fun permissionFetchedParams(entry: Map<String, String>) = PermissionFetchedParams(
        identifier = entry.safeExtract("identifier")
    )

    private data class PermissionFetchedParams(
        val identifier: TestContextKey
    )
}
