package city.smartb.im.bdd.core.privilege.permission.data

import city.smartb.im.core.privilege.domain.model.PrivilegeType
import city.smartb.im.f2.privilege.domain.permission.model.PermissionDTOBase
import city.smartb.im.f2.privilege.domain.permission.model.PermissionId
import city.smartb.im.f2.privilege.domain.permission.model.PermissionIdentifier
import city.smartb.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.RoleRepresentation
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.permission(client: KeycloakClient) = AssertionPermission(client)

class AssertionPermission(
    private val client: KeycloakClient
): AssertionApiEntity<RoleRepresentation, PermissionIdentifier, AssertionPermission.PermissionAssert>() {
    override suspend fun findById(id: PermissionIdentifier): RoleRepresentation? = client.role(id).toRepresentation()
    override suspend fun assertThat(entity: RoleRepresentation) = PermissionAssert(entity)

    inner class PermissionAssert(
        private val permission: RoleRepresentation
    ) {
        fun hasFields(
            id: PermissionId = permission.id,
            identifier: PermissionIdentifier = permission.name,
            description: String = permission.description,
        ) = also {
            Assertions.assertThat(
                permission.attributes[PermissionDTOBase::type.name]?.firstOrNull()
            ).isEqualTo(PrivilegeType.PERMISSION.name)
            Assertions.assertThat(permission.id).isEqualTo(id)
            Assertions.assertThat(permission.name).isEqualTo(identifier)
            Assertions.assertThat(permission.description).isEqualTo(description)
        }

        fun matches(permission: PermissionDTOBase) = hasFields(
            id = permission.id,
            identifier = permission.identifier,
            description = permission.description
        )
    }
}
