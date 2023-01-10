package city.smartb.im.bdd.mock

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.api.config.properties.ImProperties
import i2.keycloak.master.domain.AuthRealm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class AuthenticationProviderStub {
    @Bean
    @Primary
    fun imAuthenticationProvider(imProperties: ImProperties): ImAuthenticationProvider {
        return object: ImAuthenticationProvider {
            override suspend fun getAuth(): AuthRealm {
                return imProperties.getAuthRealm().associateBy { it.realmId }["im-test"]!!
            }
        }
    }
}
