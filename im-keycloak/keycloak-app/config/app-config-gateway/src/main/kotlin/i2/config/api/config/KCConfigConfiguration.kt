package i2.config.api.config

import i2.app.core.KcAuthConfiguration
import i2.app.core.KcAuthProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [KcConfigProperties::class, KcAuthProperties::class])
class KCConfigConfiguration: KcAuthConfiguration()
