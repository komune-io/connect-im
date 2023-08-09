package i2.init.api.config

import i2.app.core.KcAuthConfiguration
import i2.app.core.KcAuthProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [KcAuthProperties::class, KcInitProperties::class])
class KcInitConfiguration : KcAuthConfiguration()
