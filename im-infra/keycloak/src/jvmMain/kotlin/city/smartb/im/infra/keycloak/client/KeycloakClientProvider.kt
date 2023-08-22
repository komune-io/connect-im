package city.smartb.im.infra.keycloak.client

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.commons.auth.AuthenticationProvider
import org.springframework.stereotype.Service

@Service
class KeycloakClientProvider(
    private val authenticationResolver: ImAuthenticationProvider
) {
    private val cache = mutableMapOf<String, KeycloakClient>()

    suspend fun get(): KeycloakClient {
        val issuer = AuthenticationProvider.getIssuer()
        return cache.getOrPut(issuer) { KeycloakClientBuilder.build(authenticationResolver.getAuth()) }
    }
}
