package io.komune.im.bdd.core.privilege.permission.query

import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.f2.privilege.api.PermissionEndpoint
import io.komune.im.f2.privilege.domain.permission.query.PermissionListQuery
import org.springframework.beans.factory.annotation.Autowired

class PermissionListSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var permissionEndpoint: PermissionEndpoint

    init {
        When ("I list the permissions") {
            step {
                permissionList()
            }
        }
    }

    private suspend fun permissionList() {
        context.fetched.permissions = PermissionListQuery()
            .invokeWith(permissionEndpoint.permissionList())
            .items
    }
}
