package im.script.gateway.config

import im.script.gateway.conguration.config.ImScriptConfigProperties
import im.script.gateway.conguration.config.ImScriptInitProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [ImScriptConfigProperties::class, ImScriptInitProperties::class])
class AppScriptConfiguration
