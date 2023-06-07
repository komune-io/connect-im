package city.smartb.im.bdd.steps.s2.organization.assertion

import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.commons.model.Address
import city.smartb.im.organization.api.OrganizationEndpoint
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.model.ApiKey
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import org.assertj.core.api.Assertions

fun AssertionBdd.organization(api: OrganizationEndpoint) = AssertionOrganization(api)

class AssertionOrganization(
    val api: OrganizationEndpoint
) {

    suspend fun assertThat(id: OrganizationId): OrganizationAssert {
        val organization = api.organizationGet().invoke(OrganizationGetQuery(id = id)).item!!
        return assertThat(organization)
    }

    fun assertThat(entity: Organization) = OrganizationAssert(entity)

    suspend fun notExists(id: OrganizationId) {
        Assertions.assertThat(get(id)).isNull()
    }

    suspend fun get(id: OrganizationId): Organization? {
        return OrganizationGetQuery(id).invokeWith(api.organizationGet()).item
    }

    inner class OrganizationAssert(
        private val organization: Organization
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
            apiKeys: List<ApiKey> = organization.apiKeys
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
            Assertions.assertThat(organization.apiKeys).isEqualTo(apiKeys)
        }.hasAddress(address)

        fun hasAddress(address: Address?) = also {
            Assertions.assertThat(organization.address?.city).isEqualTo(address?.city)
            Assertions.assertThat(organization.address?.postalCode).isEqualTo(address?.postalCode)
            Assertions.assertThat(organization.address?.street).isEqualTo(address?.street)
        }

        fun matches(other: Organization) = hasFields(
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

        fun hasApiKey(apiKeyName: String) = also {
            Assertions.assertThat(organization.apiKeys).anyMatch { key ->
                key.name == apiKeyName
            }
        }

        fun hasNotApiKey(keyId: String) = also {
            Assertions.assertThat(organization.apiKeys).noneMatch { key ->
                key.id == keyId
            }
        }
    }
}
