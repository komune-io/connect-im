package im.script.gateway.conguration.config

import im.script.gateway.conguration.config.base.ImAuthProperties
import im.script.gateway.conguration.config.base.ImRetryProperties
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("im.script.init")
class ImScriptInitProperties(
    val auth: ImAuthProperties,
    val retry: ImRetryProperties,
    val json: String?,
)
