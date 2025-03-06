package io.komune.im.bdd

import f2.client.domain.RealmId
import io.komune.im.apikey.domain.model.ApiKey
import io.komune.im.apikey.domain.model.ApiKeyId
import io.komune.im.commons.auth.ImPermission
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.PermissionIdentifier
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.model.SpaceIdentifier
import io.komune.im.commons.model.UserId
import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.organization.domain.model.OrganizationRef
import io.komune.im.f2.privilege.domain.feature.model.Feature
import io.komune.im.f2.privilege.domain.permission.model.Permission
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.f2.user.domain.model.User
import org.springframework.stereotype.Component
import s2.bdd.auth.AuthedUser
import s2.bdd.data.TestContext
import s2.bdd.data.TestContextKey

@Component
class ImTestContext: TestContext() {
    val apikeyIds = testEntities<TestContextKey, ApiKeyId>("ApiKey")
    val featureIdentifiers = testEntities<TestContextKey, String>("Feature")
    val organizationIds = testEntities<TestContextKey, OrganizationId>("Organization")
    val permissionIdentifiers = testEntities<TestContextKey, PermissionIdentifier>("Permission")
    val realmIds = testEntities<TestContextKey, RealmId>("Realm")
    val roleIdentifiers = testEntities<TestContextKey, RoleIdentifier>("Role")
    val spaceIdentifiers = testEntities<TestContextKey, SpaceIdentifier>("Space")
    val userIds = testEntities<TestContextKey, UserId>("User")

    var realmId: RealmId = "im-test"

    private val permanentRoles = ImPermission.values()
        .asSequence()
        .map(ImPermission::identifier)
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
        lateinit var features: List<Feature>
        lateinit var organizations: List<Organization>
        lateinit var organizationRefs: List<OrganizationRef>
        lateinit var permissions: List<Permission>
        lateinit var roles: List<Role>
        lateinit var users: List<User>
    }
}
