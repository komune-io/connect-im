package city.smartb.im.bdd.steps

import city.smartb.im.bdd.data.TestContext
import city.smartb.im.organization.domain.features.command.OrganizationDeleteCommand
import city.smartb.im.organization.domain.features.query.OrganizationPageQuery
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.OrganizationFeaturesImpl
import city.smartb.im.user.domain.features.command.UserDeleteCommand
import city.smartb.im.user.domain.features.query.UserPageQuery
import city.smartb.im.user.lib.UserFeaturesImpl
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import kotlinx.coroutines.runBlocking

class EnvironmentCleanerSteps(
    private val context: TestContext,
    private val organizationEndpoint: OrganizationFeaturesImpl<Organization>,
    private val userEndpoint: UserFeaturesImpl
): En {
    init {
        Before { _ ->
            context.reset()
            cleanKeycloak()
//            resetPostgresSchema()
        }
    }

    private fun cleanKeycloak() = runBlocking {
        cleanKeycloakUsers()
        cleanKeycloakOrganizations()
    }

    private suspend fun cleanKeycloakOrganizations() {
        OrganizationPageQuery(
            page = 0,
            size = Int.MAX_VALUE,
            search = null,
            attributes = null,
            role = null,
            withDisabled = true
        ).invokeWith(organizationEndpoint.organizationPage())
            .items
            .map { OrganizationDeleteCommand(it.id).invokeWith(organizationEndpoint.organizationDelete()) }
    }

    private suspend fun cleanKeycloakUsers() {
        UserPageQuery(
            page = 0,
            size = Int.MAX_VALUE,
            organizationId = null,
            search = null,
            attributes = null,
            role = null,
            withDisabled = true
        ).invokeWith(userEndpoint.userPage())
            .items
            .map { UserDeleteCommand(it.id).invokeWith(userEndpoint.userDelete()) }
    }
}
