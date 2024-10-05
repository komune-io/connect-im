package io.komune.im.api.gateway

import io.komune.im.api.config.bean.ImAuthenticationProvider
import io.komune.im.commons.model.AuthSubRealm
import io.komune.im.api.config.properties.IMProperties
import io.komune.im.api.config.properties.toAuthRealm
import org.springframework.stereotype.Service

@Service
class ImAuthenticationProviderImpl(
    private val imConfig: IMProperties
): ImAuthenticationProvider {

    override suspend fun getAuth(): AuthSubRealm {
        return imConfig.keycloak.toAuthRealm()
    }
}
