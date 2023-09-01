package city.smartb.im.script.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("im.script.retry")
data class ImRetryProperties(
    val max: Int,
    val delayMillis: Long,
)
