package city.smartb.im.bdd

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.privilege.domain.role.model.RoleIdentifier
import city.smartb.im.user.domain.model.User
import city.smartb.im.user.domain.model.UserId
import org.springframework.stereotype.Component
import s2.bdd.data.TestContext

@Component
class ImTestContext: TestContext() {
    val apikeyIds = testEntities<String, ApiKeyId>("ApiKey")
    val organizationIds = testEntities<String, OrganizationId>("Organization")
    val roleIdentifiers = testEntities<String, RoleIdentifier>("Role")
    val userIds = testEntities<String, UserId>("User")

    final var fetched = FetchContext()
        private set

    override fun resetEnv() {
        fetched = FetchContext()
    }

    class FetchContext {
        lateinit var organizations: List<Organization>
        lateinit var users: List<User>
        lateinit var apikeys: List<ApiKey>
    }
}
