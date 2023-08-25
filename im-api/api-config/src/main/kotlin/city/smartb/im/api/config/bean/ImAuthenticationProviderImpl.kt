package city.smartb.im.api.config.bean

import city.smartb.f2.spring.boot.auth.AuthenticationProvider.getIssuer
import city.smartb.im.api.config.properties.IMProperties
import city.smartb.im.api.config.properties.toAuthRealm
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import org.springframework.stereotype.Service


@Service
class ImAuthenticationProviderImpl(
    private val imConfig: IMProperties
): ImAuthenticationProvider {

    override suspend fun getAuth(): AuthRealm {
        return imConfig.keycloak.toAuthRealm()
    }
}
