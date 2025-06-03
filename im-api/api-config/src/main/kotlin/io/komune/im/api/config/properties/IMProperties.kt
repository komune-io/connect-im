package io.komune.im.api.config.properties

import f2.client.domain.AuthRealmClientSecret
import io.komune.f2.spring.boot.auth.AuthenticationProvider
import io.komune.im.commons.model.AuthSubRealm
import org.springframework.boot.context.properties.ConfigurationProperties

const val IM_URL_PROPERTY = "connect.im"

@ConfigurationProperties(prefix = IM_URL_PROPERTY)
data class IMProperties(
    val organization: OrganizationProperties?,
    val user: UserProperties?,
    val theme: String? = null,
    val keycloak: KeycloakProperties
)
class OrganizationProperties(
    val insee: InseeProperties?
)

class UserProperties(
    val emailAsUsername: Boolean = false,
    val defaultRoleIdentifiers: String = "",
)

class KeycloakProperties(
    val url: String,
    val realm: String,
    val clientId: String,
    val clientSecret: String
)

class InseeProperties(
    val sireneApi: String,
    val token: String
)

suspend fun KeycloakProperties.toAuthRealm(): AuthSubRealm {
    val space = AuthenticationProvider.getTenant()
    return AuthSubRealm(
        space = space ?: realm,
        master = AuthRealmClientSecret(
            serverUrl = url,
            realmId = realm,
            clientId = clientId,
            clientSecret = clientSecret,
            redirectUrl = null
        )
    )
}
