package city.smartb.im.bdd.steps.s2.role.query

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.role.api.service.RoleFinderService
import city.smartb.im.role.domain.features.query.RoleGetByIdQuery
import city.smartb.im.role.domain.features.query.RoleGetByNameQuery
import city.smartb.im.role.domain.model.RoleId
import city.smartb.im.role.domain.model.RoleModel
import city.smartb.im.role.domain.model.RoleName
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired

class RoleQuerySteps : En, CucumberStepsDefinition() {

    @Autowired
    private lateinit var roleFinderService: RoleFinderService

    private var role: RoleModel? = null

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
            role = roleFinderService.getById(RoleGetByIdQuery(params.id, context.realmId)).item
        } else if (params.name != null) {
            role = roleFinderService.getByName(RoleGetByNameQuery(params.name, context.realmId)).item
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
