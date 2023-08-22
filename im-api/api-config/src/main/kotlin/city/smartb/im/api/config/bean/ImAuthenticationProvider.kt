package city.smartb.im.api.config.bean

import city.smartb.im.infra.keycloak.AuthRealm

interface ImAuthenticationProvider  {
    suspend fun getAuth(): AuthRealm
}
