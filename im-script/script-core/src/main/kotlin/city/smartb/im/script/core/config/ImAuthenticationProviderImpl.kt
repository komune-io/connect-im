package city.smartb.im.script.core.config

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.commons.auth.currentAuth
import city.smartb.im.commons.model.AuthRealm
import org.springframework.stereotype.Service

@Service
class ImAuthenticationProviderImpl: ImAuthenticationProvider {
    override suspend fun getAuth(): AuthRealm {
        return currentAuth()!!
    }
}
