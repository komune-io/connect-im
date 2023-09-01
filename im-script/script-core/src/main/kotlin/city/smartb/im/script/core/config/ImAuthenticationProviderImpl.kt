package city.smartb.im.script.core.config

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.script.core.model.AuthContext
import org.springframework.stereotype.Service
import kotlin.coroutines.coroutineContext

@Service
class ImAuthenticationProviderImpl: ImAuthenticationProvider {

    override suspend fun getAuth(): AuthRealm {
        return coroutineContext[AuthContext]!!.auth
    }
}
