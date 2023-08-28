package im.script.gateway.conguration.config.base

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.AuthRealmClientSecret
import city.smartb.im.commons.model.AuthRealmPassword

data class ImAuthProperties(
    val serverUrl: String,
    val realmId: String,
    val clientId: String,
    val clientSecret: String? = null,
    val username: String? = null,
    val password: String? = null,
)

fun ImAuthProperties.toAuthRealm(): AuthRealm {
    return if (clientSecret != null) {
            AuthRealmClientSecret(
                serverUrl = serverUrl,
                realmId = realmId,
                clientId = clientId,
                clientSecret = clientSecret,
                redirectUrl = null,
                space = realmId
            )
        } else if (username != null && password != null) {
            AuthRealmPassword(
                serverUrl = serverUrl,
                realmId = realmId,
                clientId = clientId,
                username = username,
                password = password,
                redirectUrl = "",
                space = realmId
            )

        } else {
            throw IllegalStateException("Either clientSecret or username and password must be provided")
        }
}
