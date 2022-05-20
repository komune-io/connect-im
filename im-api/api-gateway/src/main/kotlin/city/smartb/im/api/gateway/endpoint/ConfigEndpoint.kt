package city.smartb.im.api.gateway.endpoint

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.security.PermitAll
import city.smartb.im.api.config.ImKeycloakConfig
import city.smartb.im.api.config.KeycloakConfigDTO

@Configuration
class ConfigEndpoint(
    private val imKeycloakConfig: ImKeycloakConfig
) {
    @PermitAll
    @Bean
    fun keycloak(): () -> KeycloakConfigDTO = {
        KeycloakConfigDTO(
            realm = imKeycloakConfig.realm,
            authServerUrl = imKeycloakConfig.authServerUrl,
            resource = imKeycloakConfig.authRealm().clientId,
        )
    }
}
