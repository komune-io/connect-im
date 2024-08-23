package io.komune.im.bdd.core.privilege.role.query

import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.core.privilege.domain.model.RoleTarget
import io.komune.im.f2.privilege.api.RoleEndpoint
import io.komune.im.f2.privilege.domain.role.query.RoleListQuery
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.parser.extract

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
        target = entry?.extract<RoleTarget>("target")
    )

    private data class RoleListParams(
        val target: RoleTarget?
    )
}
