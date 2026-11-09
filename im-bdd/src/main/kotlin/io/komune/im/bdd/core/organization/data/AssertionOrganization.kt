package io.komune.im.bdd.core.organization.data

import io.komune.im.commons.model.Address
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.utils.parseJson
import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.infra.keycloak.client.KeycloakClient
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.GroupRepresentation
import org.slf4j.LoggerFactory
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.organization(client: KeycloakClient) = AssertionOrganization(client)

class AssertionOrganization(
    private val client: KeycloakClient
): AssertionApiEntity<GroupRepresentation, OrganizationId, AssertionOrganization.OrganizationAssert>() {

    private val logger = LoggerFactory.getLogger(AssertionOrganization::class.java)

    override suspend fun findById(id: OrganizationId): GroupRepresentation? = try {
        client.group(id).toRepresentation()
    } catch (e: JakartaNotFoundException) {
        logger.debug("Group not found with id: $id", e)
        null
    }
    override suspend fun assertThat(entity: GroupRepresentation) = OrganizationAssert(entity)

    inner class OrganizationAssert(
        private val group: GroupRepresentation
    ) {
        private val groupAttributes = group.attributes.orEmpty()
        private val singleAttributes = groupAttributes
            .mapNotNull { (key, values) -> values.firstOrNull()?.takeIf { it.isNotBlank() }?.let { key to it } }
            .toMap()

        private val groupSiret: String? = singleAttributes[Organization::siret.name]
        private val groupDescription: String? = singleAttributes[Organization::description.name]
        private val groupAddress: Address? = singleAttributes[Organization::address.name]?.parseJson()
        private val groupWebsite: String? = singleAttributes[Organization::website.name]
        private val groupEnabled: Boolean = singleAttributes[Organization::enabled.name].toBoolean()
        private val groupCreationDate: Long = singleAttributes[Organization::creationDate.name]?.toLong() ?: 0
        private val groupStatus: String? = singleAttributes[Organization::status.name]

        /** What the asserted entity holds, as fields to compare. */
        fun actual() = OrganizationFields(
            id = group.id,
            siret = groupSiret,
            name = group.name,
            description = groupDescription,
            address = groupAddress,
            website = groupWebsite,
            attributes = singleAttributes,
            roles = group.realmRoles,
            enabled = groupEnabled,
            creationDate = groupCreationDate,
            status = groupStatus,
        )

        /** Asserts the entity holds [expected], its own fields by default. */
        fun hasFields(expected: OrganizationFields = actual()) = also {
            Assertions.assertThat(group.id).isEqualTo(expected.id)
            Assertions.assertThat(groupSiret).isEqualTo(expected.siret)
            Assertions.assertThat(group.name).isEqualTo(expected.name)
            Assertions.assertThat(groupDescription).isEqualTo(expected.description)
            Assertions.assertThat(groupAddress).isEqualTo(expected.address)
            Assertions.assertThat(groupWebsite).isEqualTo(expected.website)
            Assertions.assertThat(singleAttributes).containsAllEntriesOf(expected.attributes)
            Assertions.assertThat(group.realmRoles).isEqualTo(expected.roles)
            Assertions.assertThat(groupEnabled).isEqualTo(expected.enabled)
            Assertions.assertThat(groupCreationDate).isEqualTo(expected.creationDate)
            Assertions.assertThat(groupStatus).isNotNull().isEqualTo(expected.status)
        }

        /** Compares the entity with its own fields as [change] rewrites them. */
        inline fun hasFields(change: (OrganizationFields) -> OrganizationFields) = hasFields(change(actual()))

        /** Asserts the entity holds the fields of the given model. */
        fun matches(organization: Organization) = hasFields { fields -> fields.copy(
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
        ) }

        fun isAnonym() = also {
            Assertions.assertThat(group.name).isEqualTo("anonymous")
            Assertions.assertThat(groupDescription).isNull()
            Assertions.assertThat(groupAddress).isNull()
            Assertions.assertThat(groupWebsite).isNull()
        }
    }
}

/** The fields an organization assertion compares. */
data class OrganizationFields(
    val id: OrganizationId,
    val siret: String?,
    val name: String,
    val description: String?,
    val address: Address?,
    val website: String?,
    val attributes: Map<String, String>,
    val roles: List<String>?,
    val enabled: Boolean,
    val creationDate: Long,
    val status: String?,
)
