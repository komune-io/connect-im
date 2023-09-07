package city.smartb.im.bdd

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.commons.auth.ImRole
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.PermissionIdentifier
import city.smartb.im.commons.model.RealmId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.model.SpaceIdentifier
import city.smartb.im.commons.model.UserId
import city.smartb.im.f2.organization.domain.model.OrganizationDTOBase
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase
import city.smartb.im.f2.user.domain.model.User
import org.springframework.stereotype.Component
import s2.bdd.auth.AuthedUser
import s2.bdd.data.TestContext
import s2.bdd.data.TestContextKey

@Component
class ImTestContext: TestContext() {
    val apikeyIds = testEntities<TestContextKey, ApiKeyId>("ApiKey")
    val organizationIds = testEntities<TestContextKey, OrganizationId>("Organization")
    val permissionIdentifiers = testEntities<TestContextKey, PermissionIdentifier>("Permission")
    val realmIds = testEntities<TestContextKey, RealmId>("Realm")
    val roleIdentifiers = testEntities<TestContextKey, RoleIdentifier>("Role")
    val spaceIdentifiers = testEntities<TestContextKey, SpaceIdentifier>("Space")
    val userIds = testEntities<TestContextKey, UserId>("User")

    var realmId: RealmId = "im-test"

    private val permanentRoles = ImRole.values()
        .asSequence()
        .map(ImRole::identifier)
        .plus("uma_authorization")
        .plus("offline_access")
        .toSet()

    suspend fun permanentRoles(space: String? = realmId) = permanentRoles + "default-roles-${space}"

    final var fetched = FetchContext()
        private set

    override fun resetEnv() {
        fetched = FetchContext()
        realmId = "im-test"
        // needed to define issuer with realmId
        authedUser = AuthedUser(
            id = "",
            roles = emptyArray(),
            memberOf = null
        )
    }

    class FetchContext {
        lateinit var apikeys: List<ApiKey>
        lateinit var organizations: List<OrganizationDTOBase>
        lateinit var permissions: List<PermissionDTOBase>
        lateinit var roles: List<RoleDTOBase>
        lateinit var users: List<User>
    }
}
