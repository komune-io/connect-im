package city.smartb.im.bdd.core.organization.data

import city.smartb.im.commons.model.Address
import city.smartb.im.core.organization.domain.model.OrganizationId
import city.smartb.im.f2.organization.api.OrganizationEndpoint
import city.smartb.im.f2.organization.domain.model.OrganizationDTOBase
import city.smartb.im.f2.organization.domain.query.OrganizationGetQuery
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd

fun AssertionBdd.organization(api: OrganizationEndpoint) = AssertionOrganization(api)

class AssertionOrganization(
    val api: OrganizationEndpoint
) {

    suspend fun assertThat(id: OrganizationId): OrganizationAssert {
        val organization = api.organizationGet().invoke(OrganizationGetQuery(id = id)).item!!
        return assertThat(organization)
    }

    fun assertThat(entity: OrganizationDTOBase) = OrganizationAssert(entity)

    suspend fun notExists(id: OrganizationId) {
        Assertions.assertThat(get(id)).isNull()
    }

    suspend fun get(id: OrganizationId): OrganizationDTOBase? {
        return OrganizationGetQuery(id).invokeWith(api.organizationGet()).item
    }

    inner class OrganizationAssert(
        private val organization: OrganizationDTOBase
    ) {
        fun hasFields(
            id: OrganizationId = organization.id,
            siret: String? = organization.siret,
            name: String = organization.name,
            description: String? = organization.description,
            address: Address? = organization.address,
            website: String? = organization.website,
            attributes: Map<String, String> = organization.attributes,
            roles: List<String>? = organization.roles,
            enabled: Boolean = organization.enabled,
            creationDate: Long = organization.creationDate,
        ) = also {
            Assertions.assertThat(organization.id).isEqualTo(id)
            Assertions.assertThat(organization.siret).isEqualTo(siret)
            Assertions.assertThat(organization.name).isEqualTo(name)
            Assertions.assertThat(organization.description).isEqualTo(description)
            Assertions.assertThat(organization.website).isEqualTo(website)
            Assertions.assertThat(organization.attributes).isEqualTo(attributes)
            Assertions.assertThat(organization.roles).isEqualTo(roles)
            Assertions.assertThat(organization.enabled).isEqualTo(enabled)
            Assertions.assertThat(organization.creationDate).isEqualTo(creationDate)
        }.hasAddress(address)

        fun hasAddress(address: Address?) = also {
            Assertions.assertThat(organization.address?.city).isEqualTo(address?.city)
            Assertions.assertThat(organization.address?.postalCode).isEqualTo(address?.postalCode)
            Assertions.assertThat(organization.address?.street).isEqualTo(address?.street)
        }

        fun matches(other: OrganizationDTOBase) = hasFields(
            siret = other.siret,
            name = other.name,
            description = other.description,
            address = other.address,
            website = other.website,
            attributes = other.attributes,
            roles = other.roles,
            enabled = other.enabled,
            creationDate = other.creationDate
        )

        fun isAnonymized() = also {
            Assertions.assertThat(organization.name).isEqualTo("anonymous")
            Assertions.assertThat(organization.description).isNull()
            Assertions.assertThat(organization.address).isNull()
            Assertions.assertThat(organization.website).isNull()
            Assertions.assertThat(organization.roles).isEqualTo(organization.roles)
        }

    }
}
