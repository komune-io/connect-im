package io.komune.im.core.user.api.service

import io.komune.im.commons.Transformer
import io.komune.im.core.user.domain.command.CredentialType
import io.komune.im.core.user.domain.model.UserModel
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service

@Service
class UserRepresentationTransformer(
    private val keycloakClientProvider: KeycloakClientProvider
): Transformer<UserRepresentation, UserModel>() {
    override suspend fun transform(item: UserRepresentation): UserModel {
        val client = keycloakClientProvider.getClient()
        val roles = client.user(item.id)
            .roles()
            .realmLevel()
            .listAll()
            .map { it.name }
            .minus(client.defaultRealmRole)
        val credentials = client.user(item.id).credentials()
        val mfa = credentials.map { it.type }.filter { it == CredentialType.OTP.value }
        return UserModel(
            id = item.id,
            memberOf = item.attributes?.get(UserModel::memberOf.name)?.firstOrNull(),
            email = item.email.orEmpty(),
            givenName = item.firstName.orEmpty(),
            familyName = item.lastName.orEmpty(),
            roles = roles,
            mfa = mfa,
            attributes = item.attributes.orEmpty().mapValues { (_, value) -> value.first() },
            enabled = item.isEnabled,
            creationDate = item.createdTimestamp,
            disabledBy = item.attributes?.get(UserModel::disabledBy.name)?.firstOrNull(),
            disabledDate = item.attributes?.get(UserModel::disabledDate.name)?.firstOrNull()?.toLong(),
            isApiKey = item.attributes?.get(UserModel::isApiKey.name)?.firstOrNull().toBoolean()
        )
    }
}
