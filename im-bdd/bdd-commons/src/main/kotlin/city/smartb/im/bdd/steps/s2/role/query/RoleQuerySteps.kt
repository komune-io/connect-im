package city.smartb.im.bdd.steps.s2.role.query

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.privilege.api.service.PrivilegeFinderService
import city.smartb.im.privilege.domain.RoleId
import city.smartb.im.privilege.domain.RoleName
import city.smartb.im.privilege.domain.role.model.Role
import city.smartb.im.privilege.domain.role.query.RoleGetByIdQuery
import city.smartb.im.privilege.domain.role.query.RoleGetByNameQuery
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired

class RoleQuerySteps : En, CucumberStepsDefinition() {

    @Autowired
    private lateinit var privilegeFinderService: PrivilegeFinderService

    private var role: Role? = null

    init {
        DataTableType(::roleCheckParams)

        When("I check my role:") { params: RoleCheckParams ->
            step {
                checkRole(params)
            }
        }

        Then("Role should exist") {
            Assertions.assertThat(role).isNotNull
        }
    }

    private suspend fun checkRole(params: RoleCheckParams) {
        if (params.id != null) {
            role = privilegeFinderService.getById(RoleGetByIdQuery(params.id)).item
        } else if (params.name != null) {
            role = privilegeFinderService.getByName(RoleGetByNameQuery(params.name)).item
        }
    }

    private fun roleCheckParams(entry: Map<String, String>) = RoleCheckParams(
        name = entry["name"],
        id = entry["id"]
    )

    private data class RoleCheckParams(
        val id: RoleId?,
        val name: RoleName?,
    )
}
