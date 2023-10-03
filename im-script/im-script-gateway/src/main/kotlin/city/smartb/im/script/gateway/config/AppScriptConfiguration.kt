package city.smartb.im.script.gateway.config

import city.smartb.im.script.core.config.properties.ImRetryProperties
import city.smartb.im.script.core.config.properties.ImScriptInitProperties
import city.smartb.im.script.core.config.properties.ImScriptSpaceProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [ImRetryProperties::class, ImScriptSpaceProperties::class, ImScriptInitProperties::class])
class AppScriptConfiguration
