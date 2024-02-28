package io.komune.im.script.core.config.properties

import io.komune.im.commons.model.AuthRealm
import io.komune.im.commons.model.AuthRealmClientSecret
import io.komune.im.commons.model.AuthRealmPassword
import io.komune.im.commons.model.RealmId

data class ImAuthProperties(
    val serverUrl: String,
    val realmId: String,
    val clientId: String,
    val clientSecret: String? = null,
    val username: String? = null,
    val password: String? = null,
)

fun ImAuthProperties.toAuthRealm(space: RealmId? = null): AuthRealm {
    return if (clientSecret != null) {
            AuthRealmClientSecret(
                serverUrl = serverUrl,
                realmId = realmId,
                clientId = clientId,
                clientSecret = clientSecret,
                redirectUrl = null,
                space = space ?: realmId
            )
        } else if (username != null && password != null) {
            AuthRealmPassword(
                serverUrl = serverUrl,
                realmId = realmId,
                clientId = clientId,
                username = username,
                password = password,
                redirectUrl = "",
                space = space ?: realmId
            )

        } else {
            throw IllegalStateException("Either clientSecret or username and password must be provided")
        }
}
