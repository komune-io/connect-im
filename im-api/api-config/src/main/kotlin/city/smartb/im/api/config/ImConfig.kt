package city.smartb.im.api.auth

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "i2")
@ConstructorBinding
data class ImConfig (
    val issuers: List<ImProperties>
) {
    fun getIssuersMap(): Map<String, AuthRealm> {
        if (issuers.isNullOrEmpty()) {
            return emptyMap()
        }

        return issuers.associate {
            it.uri to AuthRealmClientSecret(
                serverUrl = it.authUrl,
                realmId = it.realm,
                redirectUrl = "",
                clientId = it.clientId,
                clientSecret = it.clientSecret
            )
        }
    }
}
