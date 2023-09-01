package city.smartb.im.script.gateway.config

import city.smartb.im.script.core.config.properties.ImScriptConfigProperties
import city.smartb.im.script.core.config.properties.ImScriptInitProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [ImScriptConfigProperties::class, ImScriptInitProperties::class])
class AppScriptConfiguration
