package city.smartb.im.api.config.bean

import city.smartb.im.api.config.properties.IMProperties
import city.smartb.im.api.config.properties.toAuthRealm
import city.smartb.im.commons.model.AuthRealm
import org.springframework.stereotype.Service

@Service
class ImAuthenticationProviderImpl(
    private val imConfig: IMProperties
): ImAuthenticationProvider {

    override suspend fun getAuth(): AuthRealm {
        return imConfig.keycloak.toAuthRealm()
    }
}
