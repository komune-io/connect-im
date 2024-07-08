package io.komune.im.bdd.core.organization.data

import io.komune.im.commons.model.Address
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.utils.parseJson
import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.organization.domain.model.OrganizationRef
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.GroupRepresentation
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.organizationRef(client: KeycloakClient) = AssertionOrganizationRef(client)

class AssertionOrganizationRef(
    private val client: KeycloakClient
): AssertionApiEntity<GroupRepresentation, OrganizationId, AssertionOrganizationRef.OrganizationRefAssert>() {
    override suspend fun findById(id: OrganizationId): GroupRepresentation? = try {
        client.group(id).toRepresentation()
    } catch (e: javax.ws.rs.NotFoundException) {
        null
    }
    override suspend fun assertThat(entity: GroupRepresentation) = OrganizationRefAssert(entity)

    inner class OrganizationRefAssert(
        private val group: GroupRepresentation
    ) {
        private val singleAttributes = group.attributes
            .mapValues { (_, values) -> values.firstOrNull() }
            .filterValues { !it.isNullOrBlank() } as Map<String, String>

        fun hasFields(
            id: OrganizationId = group.id,
            name: String = group.name,
            roles: List<String>? = group.realmRoles,
        ) = also {
            Assertions.assertThat(group.id).isEqualTo(id)
            Assertions.assertThat(group.name).isEqualTo(name)
            Assertions.assertThat(roles).isEqualTo(roles)
        }

        fun matches(organization: OrganizationRef) = hasFields(
            id = organization.id,
            name = organization.name,
            roles = organization.roles,
        )

        fun isAnonym() = also {
            Assertions.assertThat(group.name).isEqualTo("anonymous")
        }
    }
}
