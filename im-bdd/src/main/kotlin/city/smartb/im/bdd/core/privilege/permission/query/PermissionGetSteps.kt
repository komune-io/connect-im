package city.smartb.im.bdd.core.privilege.permission.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.f2.privilege.api.PermissionEndpoint
import city.smartb.im.f2.privilege.domain.permission.query.PermissionGetQueryDTOBase
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.TestContextKey

class PermissionGetSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var permissionEndpoint: PermissionEndpoint

    init {
        DataTableType(::permissionGetParams)

        When ("I get the permission") {
            step {
                permissionGet(permissionGetParams(null))
            }
        }

        When ("I get the permission:") { params: PermissionGetParams ->
            step {
                permissionGet(params)
            }
        }
    }

    private suspend fun permissionGet(params: PermissionGetParams) {
        context.fetched.permissions = listOfNotNull(
            PermissionGetQueryDTOBase(
                identifier = context.permissionIdentifiers[params.identifier] ?: params.identifier
            ).invokeWith(permissionEndpoint.permissionGet()).item
        )
    }

    private fun permissionGetParams(entry: Map<String, String>?) = PermissionGetParams(
        identifier = entry?.get("identifier") ?: context.permissionIdentifiers.lastUsedKey
    )

    private data class PermissionGetParams(
        val identifier: TestContextKey
    )
}
