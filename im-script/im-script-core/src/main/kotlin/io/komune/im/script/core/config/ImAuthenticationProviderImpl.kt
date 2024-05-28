package io.komune.im.script.core.config

import io.komune.im.commons.model.AuthSubRealm
import io.komune.im.api.config.bean.ImAuthenticationProvider
import io.komune.im.commons.auth.currentAuth
import org.springframework.stereotype.Service

@Service
class ImAuthenticationProviderImpl: ImAuthenticationProvider {
    override suspend fun getAuth(): AuthSubRealm {
        return currentAuth()!!
    }
}
