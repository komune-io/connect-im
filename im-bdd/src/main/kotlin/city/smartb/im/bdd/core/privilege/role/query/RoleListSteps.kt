package city.smartb.im.bdd.core.privilege.role.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.bdd.core.privilege.role.data.extractRoleTarget
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.f2.privilege.api.RoleEndpoint
import city.smartb.im.f2.privilege.domain.role.query.RoleListQuery
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class RoleListSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var roleEndpoint: RoleEndpoint

    init {
        DataTableType(::roleListParams)

        When ("I list the roles") {
            step {
                roleList(roleListParams(null))
            }
        }

        When ("I list the roles:") { params: RoleListParams ->
            step {
                roleList(params)
            }
        }
    }

    private suspend fun roleList(params: RoleListParams) {
        context.fetched.roles = RoleListQuery(
            target = params.target?.name
        ).invokeWith(roleEndpoint.roleList()).items
    }

    private fun roleListParams(entry: Map<String, String>?) = RoleListParams(
        target = entry?.extractRoleTarget("target")
    )

    private data class RoleListParams(
        val target: RoleTarget?
    )
}
