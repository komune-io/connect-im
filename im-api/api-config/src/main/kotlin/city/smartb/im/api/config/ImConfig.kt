package city.smartb.im.api.config

import city.smartb.im.api.config.properties.ImIssuers
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "i2")
@ConstructorBinding
data class ImConfig (
    val issuers: List<ImIssuers>
) {
    fun getIssuersMap(): Map<String, AuthRealm> {
        return issuers.associate {
            it.uri to AuthRealmClientSecret(
                serverUrl = it.authUrl,
                realmId = it.realm,
                redirectUrl = "",
                clientId = it.im.clientId,
                clientSecret = it.im.clientSecret
            )
        }
    }
}
