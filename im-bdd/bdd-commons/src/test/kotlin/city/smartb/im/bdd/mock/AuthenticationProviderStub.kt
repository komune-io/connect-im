package city.smartb.im.bdd.mock

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.api.config.properties.I2Properties
import city.smartb.im.commons.model.AuthRealm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class AuthenticationProviderStub {
    @Bean
    @Primary
    fun imAuthenticationProvider(imProperties: I2Properties): ImAuthenticationProvider {
        return object: ImAuthenticationProvider {
            override suspend fun getAuth(): AuthRealm {
                return imProperties.getAuthRealm().associateBy { it.realmId }["im-test"]!!
            }
        }
    }
}
