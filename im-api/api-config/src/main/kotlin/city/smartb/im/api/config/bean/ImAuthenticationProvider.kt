package city.smartb.im.api.config.bean

import city.smartb.im.commons.model.AuthRealm

interface ImAuthenticationProvider  {
    suspend fun getAuth(): AuthRealm
}
