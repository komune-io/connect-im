package city.smartb.im.api.auth.config

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * The "im-api:api-auth" package should be imported in applications using IM clients
 * It automatically creates the keycloak config endpoint needed by front app
 * And it creates the AuthRealm bean needed by clients
 *
 * All you need is to define the variables below in your application.yml
 */
@Configuration
class ImAuthConfig {
    @Value("\${keycloak.authUrl}")
    lateinit var authServerUrl: String

    @Value("\${keycloak.realm}")
    lateinit var realm: String

    @Value("\${keycloak.clientId}")
    lateinit var clientId: String

    @Value("\${keycloak.clientSecret}")
    lateinit var clientSecret: String

    @Bean
    fun authRealm(): AuthRealm {
        return AuthRealmClientSecret (
            serverUrl = authServerUrl,
            realmId = realm,
            redirectUrl = "",
            clientId = clientId,
            clientSecret = clientSecret
        )
    }
}
