package city.smartb.im.api.config.bean

import city.smartb.im.api.config.properties.I2Properties
import city.smartb.im.commons.auth.AuthenticationProvider
import city.smartb.im.commons.model.AuthRealm

class ImAuthenticationProviderImpl(
    private val imConfig: I2Properties
): ImAuthenticationProvider {

    override suspend fun getAuth(): AuthRealm {
        val issuer = AuthenticationProvider.getIssuer()
        return imConfig.getIssuersMap()[issuer]
            ?: throw NullPointerException("No auth found for this issuer [$issuer]")
    }
}
