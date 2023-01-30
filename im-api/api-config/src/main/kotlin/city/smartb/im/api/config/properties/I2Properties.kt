package city.smartb.im.api.config.properties

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "i2")
data class I2Properties (
    val issuers: List<I2IssuersProperties>
) {
    fun getAuthRealm(): List<AuthRealm> {
        return issuers.map { toAuthRealm(it) }
    }

    fun getIssuersMap(): Map<String, AuthRealm> {
        return issuers.associate {
            it.uri to toAuthRealm(it)
        }
    }

    private fun toAuthRealm(it: I2IssuersProperties): AuthRealm = AuthRealmClientSecret(
        serverUrl = it.authUrl,
        realmId = it.realm,
        clientId = it.im.clientId,
        clientSecret = it.im.clientSecret,
        redirectUrl = it.redirectUrl
    )
}
