package io.komune.im.f2.organization.lib.config

import io.komune.im.api.config.properties.InseeProperties
import io.komune.im.f2.organization.lib.service.InseeHttpClient
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
