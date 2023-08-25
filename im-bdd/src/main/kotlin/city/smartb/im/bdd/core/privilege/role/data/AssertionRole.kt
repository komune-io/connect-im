package city.smartb.im.bdd.core.privilege.role.data

import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.f2.privilege.domain.model.PrivilegeType
import city.smartb.im.f2.privilege.domain.permission.model.PermissionIdentifier
import city.smartb.im.f2.privilege.domain.role.model.Role
import city.smartb.im.f2.privilege.domain.role.model.RoleId
import city.smartb.im.f2.privilege.domain.role.model.RoleIdentifier
import city.smartb.im.f2.privilege.domain.role.model.RoleTarget
import city.smartb.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.RoleRepresentation
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.role(client: KeycloakClient) = AssertionRole(client)

class AssertionRole(
    private val client: KeycloakClient
): AssertionApiEntity<RoleRepresentation, RoleIdentifier, AssertionRole.RoleAssert>() {
    override suspend fun findById(id: RoleIdentifier): RoleRepresentation? = client.role(id).toRepresentation()
    override suspend fun assertThat(entity: RoleRepresentation) = RoleAssert(entity)

    inner class RoleAssert(
        private val role: RoleRepresentation
    ) {
        private val roleTargets: List<RoleTarget> = role.attributes[Role::targets.name]
            .orEmpty()
            .map { RoleTarget.valueOf(it) }

        private val roleBindings: Map<RoleTarget, List<RoleIdentifier>> = role.attributes[Role::bindings.name]
            ?.firstOrNull()
            ?.parseJsonTo()
            ?: emptyMap()

        private val roleLocale: Map<String, String> = role.attributes[Role::locale.name]
            ?.firstOrNull()
            ?.parseJsonTo()
            ?: emptyMap()

        private val rolePermissions: List<PermissionIdentifier> = role.attributes[Role::permissions.name]
            .orEmpty()

        fun hasFields(
            id: RoleId = role.id,
            identifier: RoleIdentifier = role.name,
            description: String = role.description,
            targets: List<RoleTarget> = roleTargets,
            locale: Map<String, String> = roleLocale,
            bindings: Map<RoleTarget, List<RoleIdentifier>> = roleBindings,
            permissions: List<PermissionIdentifier> = rolePermissions
        ) = also {
            Assertions.assertThat(role.attributes[Role::type.name]?.firstOrNull()).isEqualTo(PrivilegeType.ROLE.name)
            Assertions.assertThat(role.id).isEqualTo(id)
            Assertions.assertThat(role.name).isEqualTo(identifier)
            Assertions.assertThat(role.description).isEqualTo(description)
            Assertions.assertThat(roleTargets).containsExactlyInAnyOrderElementsOf(targets)
            hasExactlyBindings(bindings)
            Assertions.assertThat(roleLocale).containsExactlyInAnyOrderEntriesOf(locale)
            hasExactlyPermissions(permissions.toSet())
        }

        fun matches(role: Role) = hasFields(
            id = role.id,
            identifier = role.identifier,
            description = role.description,
            targets = role.targets,
            locale = role.locale,
            bindings = role.bindings,
            permissions = role.permissions
        )

        fun hasExactlyBindings(bindings: Map<RoleTarget, List<RoleIdentifier>>) {
            Assertions.assertThat(roleBindings).hasSameSizeAs(bindings)
            Assertions.assertThat(bindings).allSatisfy { target, roles -> hasBinding(target, roles) }
        }

        fun hasBinding(target: RoleTarget, roles: List<RoleIdentifier>) {
            Assertions.assertThat(roleBindings).containsKey(target)
            Assertions.assertThat(roleBindings[target]).containsExactlyInAnyOrderElementsOf(roles)
        }

        fun hasExactlyPermissions(permissions: Set<PermissionIdentifier>) {
            Assertions.assertThat(rolePermissions).containsExactlyInAnyOrderElementsOf(permissions)
            val composites = client.role(role.name).roleComposites
            Assertions.assertThat(composites.map { it.name }).containsExactlyInAnyOrderElementsOf(permissions.toSet())
        }
    }
}
