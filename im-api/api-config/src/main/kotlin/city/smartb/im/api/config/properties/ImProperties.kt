package city.smartb.im.api.config.properties

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "i2")
@ConstructorBinding
data class ImProperties (
    val issuers: List<ImIssuersProperties>
) {
    fun getAuthRealm(): List<AuthRealm> {
        return issuers.map { toAuthRealm(it) }
    }

    fun getIssuersMap(): Map<String, AuthRealm> {
        return issuers.associate {
            it.uri to toAuthRealm(it)
        }
    }

    private fun toAuthRealm(it: ImIssuersProperties): AuthRealm = AuthRealmClientSecret(
        serverUrl = it.authUrl,
        realmId = it.realm,
        clientId = it.im.clientId,
        clientSecret = it.im.clientSecret,
    )
}
