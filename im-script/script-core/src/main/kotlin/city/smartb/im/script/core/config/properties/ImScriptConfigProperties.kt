package city.smartb.im.script.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "im.script.config")
class ImScriptConfigProperties(
    val auth: ImAuthProperties,
    val retry: ImRetryProperties,
    val json: String?,
)
