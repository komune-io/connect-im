package io.komune.im.bdd.core.privilege.feature.data

import io.komune.im.commons.model.FeatureId
import io.komune.im.commons.model.FeatureIdentifier
import io.komune.im.core.privilege.domain.model.PrivilegeType
import io.komune.im.f2.privilege.domain.feature.model.Feature
import io.komune.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.RoleRepresentation
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.feature(client: KeycloakClient) = AssertionFeature(client)

class AssertionFeature(
    private val client: KeycloakClient
): AssertionApiEntity<RoleRepresentation, FeatureIdentifier, AssertionFeature.FeatureAssert>() {
    override suspend fun findById(id: FeatureIdentifier): RoleRepresentation? = client.role(id).toRepresentation()
    override suspend fun assertThat(entity: RoleRepresentation) = FeatureAssert(entity)

    inner class FeatureAssert(
        private val feature: RoleRepresentation
    ) {
        fun hasFields(
            id: FeatureId = feature.id,
            identifier: FeatureIdentifier = feature.name,
            description: String = feature.description,
        ) = also {
            Assertions.assertThat(
                feature.attributes[Feature::type.name]?.firstOrNull()
            ).isEqualTo(PrivilegeType.FEATURE.name)
            Assertions.assertThat(feature.id).isEqualTo(id)
            Assertions.assertThat(feature.name).isEqualTo(identifier)
            Assertions.assertThat(feature.description).isEqualTo(description)
        }

        fun matches(feature: Feature) = hasFields(
            id = feature.id,
            identifier = feature.identifier,
            description = feature.description
        )
    }
}
