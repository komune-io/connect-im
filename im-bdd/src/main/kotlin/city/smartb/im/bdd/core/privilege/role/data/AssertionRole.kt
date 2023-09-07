package city.smartb.im.bdd.core.privilege.role.data

import city.smartb.im.commons.model.PermissionIdentifier
import city.smartb.im.commons.model.RoleId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.utils.parseJson
import city.smartb.im.core.privilege.domain.model.PrivilegeType
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase
import city.smartb.im.infra.keycloak.client.KeycloakClient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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
        private val roleTargets: List<RoleTarget> = role.attributes[RoleDTOBase::targets.name]
            .orEmpty()
            .map { RoleTarget.valueOf(it) }

        private val roleBindings: Map<RoleTarget, List<RoleIdentifier>> = role.attributes[RoleDTOBase::bindings.name]
            ?.firstOrNull()
            ?.parseJson<Map<String, List<RoleIdentifier>>>()
            ?.mapKeys { (target) -> RoleTarget.valueOf(target) }
            .orEmpty()

        private val roleLocale: Map<String, String> = role.attributes[RoleDTOBase::locale.name]
            ?.firstOrNull()
            ?.parseJson()
            ?: emptyMap()

        private val rolePermissions: List<PermissionIdentifier> = role.attributes[RoleDTOBase::permissions.name]
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
            Assertions.assertThat(role.attributes[RoleDTOBase::type.name]?.firstOrNull()).isEqualTo(PrivilegeType.ROLE.name)
            Assertions.assertThat(role.id).isEqualTo(id)
            Assertions.assertThat(role.name).isEqualTo(identifier)
            Assertions.assertThat(role.description).isEqualTo(description)
            Assertions.assertThat(roleTargets).containsExactlyInAnyOrderElementsOf(targets)
            hasExactlyBindings(bindings)
            Assertions.assertThat(roleLocale).containsExactlyInAnyOrderEntriesOf(locale)
            hasExactlyPermissions(permissions.toSet())
        }

        suspend fun matches(role: RoleDTOBase): Unit = coroutineScope {
            hasFields(
                id = role.id,
                identifier = role.identifier,
                description = role.description,
                targets = role.targets.map { RoleTarget.valueOf(it) },
                locale = role.locale,
                bindings = role.bindings
                    .mapKeys { (target) -> RoleTarget.valueOf(target) }
                    .mapValues { (_, roles) -> roles.map(RoleDTOBase::identifier) },
                permissions = role.permissions
            )

            role.bindings.values.flatten().map { role ->
                async { assertThatId(role.identifier).matches(role) }
            }.awaitAll()
        }

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
