package city.smartb.im.bdd.core.privilege.permission.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.bdd.core.privilege.permission.data.permission
import city.smartb.im.f2.privilege.domain.permission.model.Permission
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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

        Then("I should receive a list of permissions:") { dataTable: DataTable ->
            step {
                dataTable.asList(PermissionFetchedParams::class.java)
                    .map(PermissionFetchedParams::identifier)
                    .let { assertFetchedPermissions(it) }
            }
        }
    }

    private suspend fun assertFetchedPermissions(identifiers: List<TestContextKey>) = coroutineScope {
        val fetchedIdentifiers = context.fetched.permissions.map(Permission::identifier)
        val expectedIdentifiers = identifiers.map(context.permissionIdentifiers::safeGet)
        Assertions.assertThat(fetchedIdentifiers).containsExactlyInAnyOrderElementsOf(expectedIdentifiers)

        val permissionAsserter = AssertionBdd.permission(context.keycloakClient())
        context.fetched.permissions.map { permission ->
            async { permissionAsserter.assertThatId(permission.identifier).matches(permission) }
        }.awaitAll()
    }

    private fun permissionFetchedParams(entry: Map<String, String>) = PermissionFetchedParams(
        identifier = entry.safeExtract("identifier")
    )

    private data class PermissionFetchedParams(
        val identifier: TestContextKey
    )
}
