package city.smartb.im.bdd.core.organization.data

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.utils.parseJson
import city.smartb.im.f2.organization.domain.model.OrganizationDTOBase
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase
import city.smartb.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.GroupRepresentation
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.organization(client: KeycloakClient) = AssertionOrganization(client)

class AssertionOrganization(
    private val client: KeycloakClient
): AssertionApiEntity<GroupRepresentation, OrganizationId, AssertionOrganization.OrganizationAssert>() {
    override suspend fun findById(id: OrganizationId): GroupRepresentation? = try {
        client.group(id).toRepresentation()
    } catch (e: javax.ws.rs.NotFoundException) {
        null
    }
    override suspend fun assertThat(entity: GroupRepresentation) = OrganizationAssert(entity)

    inner class OrganizationAssert(
        private val group: GroupRepresentation
    ) {
        private val singleAttributes = group.attributes
            .mapValues { (_, values) -> values.firstOrNull() }
            .filterValues { !it.isNullOrBlank() } as Map<String, String>

        private val groupSiret: String? = singleAttributes[OrganizationDTOBase::siret.name]
        private val groupDescription: String? = singleAttributes[OrganizationDTOBase::description.name]
        private val groupAddress: Address? = singleAttributes[OrganizationDTOBase::address.name]?.parseJson()
        private val groupWebsite: String? = singleAttributes[OrganizationDTOBase::website.name]
        private val groupEnabled: Boolean = singleAttributes[OrganizationDTOBase::enabled.name].toBoolean()
        private val groupCreationDate: Long = singleAttributes[OrganizationDTOBase::creationDate.name]?.toLong() ?: 0

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
            creationDate: Long = groupCreationDate
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
        }

        fun matches(organization: OrganizationDTOBase) = hasFields(
            id = organization.id,
            siret = organization.siret,
            name = organization.name,
            description = organization.description,
            address = organization.address,
            website = organization.website,
            attributes = organization.attributes,
            roles = organization.roles.map(RoleDTOBase::identifier),
            enabled = organization.enabled,
            creationDate = organization.creationDate
        )

        fun isAnonym() = also {
            Assertions.assertThat(group.name).isEqualTo("anonymous")
            Assertions.assertThat(groupDescription).isNull()
            Assertions.assertThat(groupAddress).isNull()
            Assertions.assertThat(groupWebsite).isNull()
        }
    }
}
