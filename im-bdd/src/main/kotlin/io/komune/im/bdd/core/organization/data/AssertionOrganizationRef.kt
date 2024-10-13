package io.komune.im.bdd.core.organization.data

import io.komune.im.commons.model.OrganizationId
import io.komune.im.f2.organization.domain.model.OrganizationRef
import io.komune.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.GroupRepresentation
import org.slf4j.LoggerFactory
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException

fun AssertionBdd.organizationRef(client: KeycloakClient) = AssertionOrganizationRef(client)

class AssertionOrganizationRef(
    private val client: KeycloakClient
): AssertionApiEntity<GroupRepresentation, OrganizationId, AssertionOrganizationRef.OrganizationRefAssert>() {

    private val logger = LoggerFactory.getLogger(AssertionOrganizationRef::class.java)

    override suspend fun findById(id: OrganizationId): GroupRepresentation? = try {
        client.group(id).toRepresentation()
    } catch (e: JakartaNotFoundException) {
        logger.debug("Group not found with id: $id")
        null
    }
    override suspend fun assertThat(entity: GroupRepresentation) = OrganizationRefAssert(entity)

    inner class OrganizationRefAssert(
        private val group: GroupRepresentation
    ) {
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
