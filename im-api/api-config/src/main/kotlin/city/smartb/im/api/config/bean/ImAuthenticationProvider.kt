package city.smartb.im.api.config.bean

import i2.keycloak.master.domain.AuthRealm

interface ImAuthenticationProvider  {

    suspend fun getAuth(): AuthRealm
}