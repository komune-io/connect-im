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
data class OrganizationProperties(
    val insee: InseeProperties?
)

data class UserProperties(
    @Suppress("BooleanPropertyNaming") // wire name: the connect.im.user.email-as-username config key
    val emailAsUsername: Boolean = false,
    val defaultRoleIdentifiers: String = "",
    val welcomeLinkLifespanSeconds: Int = DEFAULT_WELCOME_LINK_LIFESPAN_SECONDS,
) {
    companion object {
        const val DEFAULT_WELCOME_LINK_LIFESPAN_SECONDS = 7 * 24 * 60 * 60
    }
}

data class KeycloakProperties(
    val url: String,
    val realm: String,
    val clientId: String,
    val clientSecret: String
) {
    override fun toString() = "KeycloakProperties(url=$url, realm=$realm, clientId=$clientId, clientSecret=***)"
}

data class InseeProperties(
    val sireneApi: String,
    val token: String
) {
    override fun toString() = "InseeProperties(sireneApi=$sireneApi, token=***)"
}

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
