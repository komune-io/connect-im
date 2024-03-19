package io.komune.im.api.config

import io.komune.im.api.config.properties.IMProperties
import io.komune.im.api.config.properties.InseeProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(IMProperties::class)
class IMConfig {

    @Bean
    @ConditionalOnBean(IMProperties::class)
    fun inseeProperties(imProperties: IMProperties): InseeProperties? {
        return imProperties.organization?.insee
    }
}
