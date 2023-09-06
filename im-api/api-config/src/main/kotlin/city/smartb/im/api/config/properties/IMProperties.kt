package city.smartb.im.api.config.properties

import city.smartb.f2.spring.boot.auth.AuthenticationProvider
import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.AuthRealmClientSecret
import org.springframework.boot.context.properties.ConfigurationProperties

const val IM_URL_PROPERTY = "connect.im"

@ConfigurationProperties(prefix = IM_URL_PROPERTY)
data class IMProperties (
    val organization: OrganizationProperties?,
    val smtp: Map<String, String>? = null,
    val theme: String? = null,
    val user: UserProperties?,
    val keycloak: KeycloakProperties
)
class OrganizationProperties(
    val insee: InseeProperties?
)
class UserProperties(
    val action: UserActionProperties?
)

class KeycloakProperties(
    val url: String,
    val realm: String,
    val clientId: String,
    val clientSecret: String
)

class UserActionProperties(
    val useJwtClientId: Boolean? = false
)

class InseeProperties(
    val sireneApi: String,
    val token: String
)

suspend fun KeycloakProperties.toAuthRealm(): AuthRealm {
    val space = AuthenticationProvider.getIssuer()?.split("/")?.last()
    return AuthRealmClientSecret(
        serverUrl = url,
        realmId = realm,
        clientId = clientId,
        clientSecret = clientSecret,
        redirectUrl = null,
        space = space ?: realm
    )
}
