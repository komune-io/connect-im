package io.komune.im.api.config.bean

import io.komune.im.commons.model.AuthRealm

interface ImAuthenticationProvider  {
    suspend fun getAuth(): AuthRealm
}
