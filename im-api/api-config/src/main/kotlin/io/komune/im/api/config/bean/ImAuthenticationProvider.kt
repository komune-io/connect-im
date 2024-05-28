package io.komune.im.api.config.bean

import io.komune.im.commons.model.AuthSubRealm

interface ImAuthenticationProvider  {
    suspend fun getAuth(): AuthSubRealm
}
