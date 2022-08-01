package city.smartb.im.api.config.bean

import city.smartb.i2.spring.boot.auth.AuthenticationProvider.getIssuer
import city.smartb.im.api.config.properties.ImProperties
import i2.keycloak.master.domain.AuthRealm
import org.springframework.stereotype.Service

@Service
class ImAuthenticationResolver(
    private val imConfig: ImProperties
) {

    suspend fun getAuth(): AuthRealm {
        return imConfig.getIssuersMap()[getIssuer()]
            ?: throw NullPointerException("No auth found for this issuer ${getIssuer()}")
    }
}
