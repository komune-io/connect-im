package city.smartb.im.api.auth

import city.smartb.im.api.auth.config.ImAuthConfig
import city.smartb.im.api.auth.config.KeycloakConfigDTO
import javax.annotation.security.PermitAll
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConfigEndpoint(
    private val imAuthConfig: ImAuthConfig
) {
    @PermitAll
    @Bean
    fun keycloak(): () -> KeycloakConfigDTO = {
        KeycloakConfigDTO(
            realm = imAuthConfig.realm,
            authServerUrl = imAuthConfig.authServerUrl,
            resource = imAuthConfig.clientId,
        )
    }
}
