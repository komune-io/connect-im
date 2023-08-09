package im.script.gateway.conguration.config.base

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import i2.keycloak.master.domain.AuthRealmPassword

data class ImAuthProperties(
    val serverUrl: String,
    val realm: String,
    val clientId: String,
    val clientSecret: String? = null,
    val username: String? = null,
    val password: String? = null,
)

fun ImAuthProperties.toAuthRealm(): AuthRealm {
    return if (clientSecret != null) {
            AuthRealmClientSecret(
                serverUrl = serverUrl,
                realmId = realm,
                clientId = clientId,
                clientSecret = clientSecret,
                redirectUrl = null
            )
        } else if (username != null && password != null) {
            AuthRealmPassword(
                serverUrl = serverUrl,
                realmId = realm,
                clientId = clientId,
                username = username,
                password = password,
                redirectUrl = ""
            )

        } else {
            throw IllegalStateException("Either clientSecret or username and password must be provided")
        }
}
