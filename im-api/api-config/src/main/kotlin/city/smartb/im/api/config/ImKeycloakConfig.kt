package city.smartb.im.api.config

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import i2.keycloak.master.domain.AuthRealmPassword
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ImKeycloakConfig {

    @Value("\${i2.auth-client}")
    lateinit var authClient: String

    @Value("\${keycloak.auth-server-url}")
    lateinit var authServerUrl: String

    @Value("\${keycloak.realm}")
    lateinit var realm: String

    @Bean
    @ConfigurationProperties(prefix = "keycloak.clients")
    fun clients(): Map<String, KeycloakClientProperties> = HashMap()

    @Bean
    fun authRealm(): AuthRealm {
        val client = clients()[authClient]
            ?: throw IllegalArgumentException("The keycloak client [$authClient] has not been defined")

        val authRealm = client.realm ?: realm

        return when {
            client.clientSecret != null -> AuthRealmClientSecret(
                serverUrl = authServerUrl,
                realmId = authRealm,
                redirectUrl = "",
                clientId = client.clientId,
                clientSecret = client.clientSecret
            )
            client.username != null && client.password != null -> AuthRealmPassword(
                serverUrl = authServerUrl,
                realmId = authRealm,
                redirectUrl = "",
                clientId = client.clientId,
                username = client.username,
                password = client.password
            )
            else -> throw IllegalArgumentException(
                "The keycloak client [$authClient] must have either a clientSecret or a pair of username/password"
            )
        }
    }
}
