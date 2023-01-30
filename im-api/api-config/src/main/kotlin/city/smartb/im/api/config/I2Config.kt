package city.smartb.im.api.config

import city.smartb.im.api.config.bean.AuthRealmDeserializer
import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.api.config.bean.ImAuthenticationProviderImpl
import city.smartb.im.api.config.properties.I2Properties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import i2.keycloak.master.domain.AuthRealm
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(I2Properties::class)
class I2Config {

    @Bean
    @ConditionalOnMissingBean(ImAuthenticationProvider::class)
    fun imAuthenticationProvider(imProperties: I2Properties): ImAuthenticationProvider {
        return ImAuthenticationProviderImpl(imProperties)
    }
    @Bean
    fun authRealmJacksonDeserializer(): ObjectMapper = jacksonObjectMapper().also { mapper ->
        val module = SimpleModule()
        module.addDeserializer(AuthRealm::class.java, AuthRealmDeserializer())
        mapper.registerModule(module)
    }

}
