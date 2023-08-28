package city.smartb.im.bdd.core.privilege.permission.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.f2.privilege.api.PermissionEndpoint
import city.smartb.im.f2.privilege.domain.permission.query.PermissionListQueryDTOBase
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
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
        context.fetched.permissions = PermissionListQueryDTOBase()
            .invokeWith(permissionEndpoint.permissionList())
            .items
    }
}
