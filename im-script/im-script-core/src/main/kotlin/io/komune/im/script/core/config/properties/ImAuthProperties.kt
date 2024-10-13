package io.komune.im.script.core.config.properties

import f2.client.domain.AuthRealmClientSecret
import f2.client.domain.AuthRealmPassword
import f2.client.domain.RealmId
import io.komune.im.commons.model.AuthSubRealm

data class ImAuthProperties(
    val serverUrl: String,
    val realmId: String,
    val clientId: String,
    val clientSecret: String? = null,
    val username: String? = null,
    val password: String? = null,
)

@Suppress("UseCheckOrError")
fun ImAuthProperties.toAuthRealm(space: RealmId? = null): AuthSubRealm {
    return if (clientSecret != null) {
        AuthSubRealm(
            master =  AuthRealmClientSecret(
                serverUrl = serverUrl,
                realmId = realmId,
                clientId = clientId,
                clientSecret = clientSecret,
                redirectUrl = null,
            ),
            space = space ?: realmId,
        )

        } else if (username != null && password != null) {
            AuthSubRealm(
                master =  AuthRealmPassword(
                    serverUrl = serverUrl,
                    realmId = realmId,
                    clientId = clientId,
                    username = username,
                    password = password,
                    redirectUrl = "",
                ),
                space = space ?: realmId,
            )
        } else {
            throw IllegalStateException("Either clientSecret or username and password must be provided")
        }
}
