package io.komune.im.bdd.core.organization.data

import io.komune.im.commons.model.Address
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.utils.parseJson
import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.GroupRepresentation
import org.slf4j.LoggerFactory
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException

fun AssertionBdd.organization(client: KeycloakClient) = AssertionOrganization(client)

class AssertionOrganization(
    private val client: KeycloakClient
): AssertionApiEntity<GroupRepresentation, OrganizationId, AssertionOrganization.OrganizationAssert>() {

    private val logger = LoggerFactory.getLogger(AssertionOrganizationRef::class.java)

    override suspend fun findById(id: OrganizationId): GroupRepresentation? = try {
        client.group(id).toRepresentation()
    } catch (e: JakartaNotFoundException) {
        logger.debug("Group not found with id: $id")
        null
    }
    override suspend fun assertThat(entity: GroupRepresentation) = OrganizationAssert(entity)

    inner class OrganizationAssert(
        private val group: GroupRepresentation
    ) {
        private val groupAttributes = group.attributes.orEmpty()
        private val singleAttributes = groupAttributes
            .mapValues { (_, values) -> values.firstOrNull() }
            .filterValues { !it.isNullOrBlank() } as Map<String, String>

        private val groupSiret: String? = singleAttributes[Organization::siret.name]
        private val groupDescription: String? = singleAttributes[Organization::description.name]
        private val groupAddress: Address? = singleAttributes[Organization::address.name]?.parseJson()
        private val groupWebsite: String? = singleAttributes[Organization::website.name]
        private val groupEnabled: Boolean = singleAttributes[Organization::enabled.name].toBoolean()
        private val groupCreationDate: Long = singleAttributes[Organization::creationDate.name]?.toLong() ?: 0
        private val groupStatus: String? = singleAttributes[Organization::status.name]

        fun hasFields(
            id: OrganizationId = group.id,
            siret: String? = groupSiret,
            name: String = group.name,
            description: String? = groupDescription,
            address: Address? = groupAddress,
            website: String? = groupWebsite,
            attributes: Map<String, String> = singleAttributes,
            roles: List<String>? = group.realmRoles,
            enabled: Boolean = groupEnabled,
            creationDate: Long = groupCreationDate,
            status: String? = groupStatus,
        ) = also {
            Assertions.assertThat(group.id).isEqualTo(id)
            Assertions.assertThat(groupSiret).isEqualTo(siret)
            Assertions.assertThat(group.name).isEqualTo(name)
            Assertions.assertThat(groupDescription).isEqualTo(description)
            Assertions.assertThat(groupAddress).isEqualTo(address)
            Assertions.assertThat(groupWebsite).isEqualTo(website)
            Assertions.assertThat(singleAttributes).containsAllEntriesOf(attributes)
            Assertions.assertThat(roles).isEqualTo(roles)
            Assertions.assertThat(groupEnabled).isEqualTo(enabled)
            Assertions.assertThat(groupCreationDate).isEqualTo(creationDate)
            Assertions.assertThat(groupStatus).isNotNull().isEqualTo(status)
        }

        fun matches(organization: Organization) = hasFields(
            id = organization.id,
            siret = organization.siret,
            name = organization.name,
            description = organization.description,
            address = organization.address,
            website = organization.website,
            attributes = organization.attributes,
            roles = organization.roles.map(Role::identifier),
            enabled = organization.enabled,
            creationDate = organization.creationDate,
            status = organization.status
        )

        fun isAnonym() = also {
            Assertions.assertThat(group.name).isEqualTo("anonymous")
            Assertions.assertThat(groupDescription).isNull()
            Assertions.assertThat(groupAddress).isNull()
            Assertions.assertThat(groupWebsite).isNull()
        }
    }
}
