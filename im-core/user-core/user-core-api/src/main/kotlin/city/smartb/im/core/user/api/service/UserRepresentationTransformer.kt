package city.smartb.im.core.user.api.service

import city.smartb.im.commons.Transformer
import city.smartb.im.core.user.domain.model.UserModel
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service

@Service
class UserRepresentationTransformer(
    private val keycloakClientProvider: KeycloakClientProvider
): Transformer<UserRepresentation, UserModel>() {
    override suspend fun transform(item: UserRepresentation): UserModel {
        val client = keycloakClientProvider.get()
        val roles = client.user(item.id)
            .roles()
            .realmLevel()
            .listAll()
            .map { it.name }
            .minus(client.defaultRealmRole)

        return UserModel(
            id = item.id,
            memberOf = item.attributes[UserModel::memberOf.name]?.firstOrNull(),
            email = item.email,
            givenName = item.firstName,
            familyName = item.lastName,
            roles = roles,
            attributes = item.attributes.orEmpty().mapValues { (_, value) -> value.first() },
            enabled = item.isEnabled,
            creationDate = item.createdTimestamp,
            disabledBy = item.attributes[UserModel::disabledBy.name]?.firstOrNull(),
            disabledDate = item.attributes[UserModel::disabledDate.name]?.firstOrNull()?.toLong(),
        )
    }
}
