package city.smartb.im.organization.lib.config

import city.smartb.im.api.config.properties.InseeProperties
import city.smartb.im.organization.lib.service.InseeHttpClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConditionalOnBean(InseeProperties::class)
@Configuration(proxyBeanMethods = true)
class InseeClientConfig {

    @Bean
    fun inseeHttpClient(
        inseeProperties: InseeProperties?
    ): InseeHttpClient? {
        return inseeProperties?.let { InseeHttpClient(inseeProperties) }
    }

}
