package io.komune.im.bdd.mock

import io.komune.im.api.config.bean.ImAuthenticationProvider
import io.komune.im.api.config.properties.IMProperties
import io.komune.im.api.config.properties.toAuthRealm
import io.komune.im.commons.model.AuthRealm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class AuthenticationProviderStub {
    @Bean
    @Primary
    fun imAuthenticationProvider(imProperties: IMProperties): ImAuthenticationProvider {
        return object: ImAuthenticationProvider {
            override suspend fun getAuth(): AuthRealm {
                return imProperties.keycloak.toAuthRealm()
            }
        }
    }
}
