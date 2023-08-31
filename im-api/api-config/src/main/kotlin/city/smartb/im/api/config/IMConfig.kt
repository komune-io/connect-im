package city.smartb.im.api.config

import city.smartb.im.api.config.properties.IMProperties
import city.smartb.im.api.config.properties.InseeProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(IMProperties::class)
class IMConfig {

    @Bean
    fun imProperties(imProperties: IMProperties): InseeProperties? {
        return imProperties.organization?.insee
    }
}
