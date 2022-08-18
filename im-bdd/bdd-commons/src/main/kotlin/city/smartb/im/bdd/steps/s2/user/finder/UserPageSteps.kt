package city.smartb.im.bdd.steps.s2.user.finder

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.data.parser.parseNullableOrDefault
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.user.api.UserEndpoint
import city.smartb.im.user.domain.features.query.UserPageQuery
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class UserPageSteps: En, CucumberStepsDefinition() {

    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    init {
        DataTableType(::userPageParams)

        When ("I get a page of users") {
            step {
                fetchUserPage(userPageParams(null))
            }
        }

        When ("I get a page of users:") { params: UserPageParams ->
            step {
                fetchUserPage(params)
            }
        }
    }

    private suspend fun fetchUserPage(params: UserPageParams) {
        context.fetched.users = UserPageQuery(
            organizationId = params.organizationId,
            search = params.search,
            role = params.role,
            withDisabled = params.withDisable,
            page = params.page,
            size = params.size,
            attributes = null
        ).invokeWith(userEndpoint.userPage()).items
    }

    private fun userPageParams(entry: Map<String, String>?) = UserPageParams(
        organizationId = entry?.get("organizationId").parseNullableOrDefault(context.organizationIds.lastUsedOrNull),
        search = entry?.get("search"),
        role = entry?.get("role"),
//        attributes = entry?.get("attr"),
        withDisable = entry?.get("withDisabled").toBoolean(),
        page = entry?.get("page")?.toInt(),
        size = entry?.get("size")?.toInt(),
    )

    private data class UserPageParams(
        val organizationId: OrganizationId?,
        val search: String?,
        val role: String?,
//        val attributes: Map<String, String>?,
        val withDisable: Boolean,
        val page: Int?,
        val size: Int?
    )
}
