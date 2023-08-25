package city.smartb.im.bdd

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.commons.model.RealmId
import city.smartb.im.f2.privilege.domain.permission.model.Permission
import city.smartb.im.f2.privilege.domain.permission.model.PermissionIdentifier
import city.smartb.im.f2.privilege.domain.role.model.Role
import city.smartb.im.f2.privilege.domain.role.model.RoleIdentifier
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.user.domain.model.User
import city.smartb.im.user.domain.model.UserId
import org.springframework.stereotype.Component
import s2.bdd.data.TestContext
import s2.bdd.data.TestContextKey

@Component
class ImTestContext(
    private val keycloakClientProvider: KeycloakClientProvider
): TestContext() {
    val apikeyIds = testEntities<TestContextKey, ApiKeyId>("ApiKey")
    val organizationIds = testEntities<TestContextKey, OrganizationId>("Organization")
    val permissionIdentifiers = testEntities<TestContextKey, PermissionIdentifier>("Permission")
    val realmIds = testEntities<TestContextKey, RealmId>("Realm")
    val roleIdentifiers = testEntities<TestContextKey, RoleIdentifier>("Role")
    val userIds = testEntities<TestContextKey, UserId>("User")

    var realmId: RealmId? = null
    suspend fun keycloakClient() = keycloakClientProvider.getFor(realmId)

    final var fetched = FetchContext()
        private set

    override fun resetEnv() {
        fetched = FetchContext()
        realmId = null
        authedUser = null
    }

    class FetchContext {
        lateinit var apikeys: List<ApiKey>
        lateinit var organizations: List<Organization>
        lateinit var permissions: List<Permission>
        lateinit var roles: List<Role>
        lateinit var users: List<User>
    }
}
