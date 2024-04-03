package io.komune.im.api.config.bean

import io.komune.im.api.config.properties.IMProperties
import io.komune.im.api.config.properties.toAuthRealm
import io.komune.im.commons.model.AuthRealm
import org.springframework.stereotype.Service

@Service
class ImAuthenticationProviderImpl(
    private val imConfig: IMProperties
): ImAuthenticationProvider {

    override suspend fun getAuth(): AuthRealm {
        return imConfig.keycloak.toAuthRealm()
    }
}
