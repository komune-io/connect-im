package io.komune.im.bdd.core.user.query

import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.commons.model.OrganizationId
import io.komune.im.f2.user.api.UserEndpoint
import io.komune.im.f2.user.domain.query.UserPageQuery

/** Cucumber steps: user page. */
class UserPageSteps(
    private val userEndpoint: UserEndpoint,
): En, ImCucumberStepsDefinition() {

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
            organizationId = params.organizationId?.let { context.organizationIds[it] ?: it },
            name = params.name,
            email = params.email,
            role = params.role,
            withDisabled = params.isWithDisabled,
            offset = params.offset,
            limit = params.limit,
            attributes = null
        ).invokeWith(userEndpoint.userPage()).items
    }

    private fun userPageParams(entry: Map<String, String>?) = UserPageParams(
        organizationId = entry?.get("organizationId").parseNullableOrDefault(null),
        name = entry?.get("name"),
        email = entry?.get("email"),
        role = entry?.get("role"),
        isWithDisabled = entry?.get("withDisabled").toBoolean(),
        offset = entry?.get("offset")?.toInt(),
        limit = entry?.get("limit")?.toInt(),
    )

    private data class UserPageParams(
        val organizationId: OrganizationId?,
        val name: String?,
        val email: String?,
        val role: String?,
        val isWithDisabled: Boolean,
        val offset: Int?,
        val limit: Int?
    )
}
