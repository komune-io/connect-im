package im.script.gateway.conguration.config.base

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("im.script.retry")
data class ImRetryProperties(
    val max: Int,
    val delayMillis: Long,
)
