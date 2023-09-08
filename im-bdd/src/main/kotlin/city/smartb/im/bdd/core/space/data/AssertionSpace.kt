package city.smartb.im.bdd.core.space.data

import city.smartb.im.commons.model.SpaceIdentifier
import city.smartb.im.f2.space.domain.model.Space
import city.smartb.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.RealmRepresentation
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.space(client: KeycloakClient) = AssertionSpace(client)

class AssertionSpace(
    private val client: KeycloakClient
): AssertionApiEntity<RealmRepresentation, SpaceIdentifier, AssertionSpace.SpaceAssert>() {
    override suspend fun findById(id: SpaceIdentifier): RealmRepresentation? = client.realm(id).toRepresentation()
    override suspend fun assertThat(entity: RealmRepresentation) = SpaceAssert(entity)

    inner class SpaceAssert(
        private val space: RealmRepresentation
    ) {
        fun hasFields(
            identifier: SpaceIdentifier = space.id,
        ) = also {
            Assertions.assertThat(space.id).isEqualTo(identifier)
        }

        fun matches(space: Space) = hasFields(
            identifier = space.identifier
        )
    }
}
