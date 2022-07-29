package city.smartb.im.api.config.bean

import city.smartb.i2.spring.boot.auth.AuthenticationProvider.getIssuer
import city.smartb.im.api.config.ImConfig
import i2.keycloak.master.domain.AuthRealm
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(ImConfig::class)
class ImAuthenticationResolver(
    private val imConfig: ImConfig
) {

    suspend fun getAuth(): AuthRealm {
        return imConfig.getIssuersMap()[getIssuer()]
            ?: throw NullPointerException("No auth found for this issuer ${getIssuer()}")
    }
}
