package city.smartb.im.api.auth

import city.smartb.i2.spring.boot.auth.AuthenticationProvider.getIssuer
import i2.keycloak.master.domain.AuthRealm
import org.springframework.stereotype.Service

@Service
class ImAuthenticationResolver(
    private val imConfig: ImConfig
) {

    suspend fun getAuth(): AuthRealm {
        return imConfig.getIssuersMap()[getIssuer()]
            ?: throw NullPointerException("No auth found for this issuer ${getIssuer()}")
    }
}