package i2.config.api.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "im.config")
class KcConfigProperties(
    val maxRetries: Int,
    val retryDelayMillis: Long,
    var json: String,
)
