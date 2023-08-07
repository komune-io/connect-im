package i2.app.core

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import i2.keycloak.master.domain.AuthRealmPassword
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean

@ConfigurationProperties("i2.keycloak")
data class KcAuthProperties(
    val serverUrl: String,
    val realm: String,
    val clientId: String,
    val clientSecret: String? = null,
    val username: String? = null,
    val password: String? = null,
)

@SuppressWarnings("UnnecessaryAbstractClass")
abstract class KcAuthConfiguration {

    @Bean
    open fun authRealm(properties: KcAuthProperties): AuthRealm {
        return if (properties.clientSecret != null) {
            AuthRealmClientSecret(
                serverUrl = properties.serverUrl,
                realmId = properties.realm,
                clientId = properties.clientId,
                clientSecret = properties.clientSecret,
                redirectUrl = null
            )
        } else if (properties.username != null && properties.password != null) {
            AuthRealmPassword(
                serverUrl = properties.serverUrl,
                realmId = properties.realm,
                clientId = properties.clientId,
                username = properties.username,
                password = properties.password,
                redirectUrl = ""
            )
        } else {
            throw IllegalStateException("Either clientSecret or username and password must be provided")
        }
    }
}
