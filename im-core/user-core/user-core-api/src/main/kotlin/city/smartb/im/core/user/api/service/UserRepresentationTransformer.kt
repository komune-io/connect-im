package city.smartb.im.core.user.api.service

import city.smartb.im.commons.Transformer
import city.smartb.im.core.user.domain.model.User
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service

@Service
class UserRepresentationTransformer(
    private val keycloakClientProvider: KeycloakClientProvider
): Transformer<UserRepresentation, User>() {
    override suspend fun transform(item: UserRepresentation): User {
        val client = keycloakClientProvider.get()
        val roles = client.user(item.id)
            .roles()
            .realmLevel()
            .listAll()
            .map { it.name }
            .minus(client.defaultRealmRole)

        return User(
            id = item.id,
            memberOf = item.attributes[User::memberOf.name]?.firstOrNull(),
            email = item.email,
            givenName = item.firstName,
            familyName = item.lastName,
            roles = roles,
            attributes = item.attributes.orEmpty().mapValues { (_, value) -> value.first() },
            enabled = item.isEnabled,
            creationDate = item.createdTimestamp,
            disabledBy = item.attributes[User::disabledBy.name]?.firstOrNull(),
            disabledDate = item.attributes[User::disabledDate.name]?.firstOrNull()?.toLong(),
        )
    }
}