package io.komune.f2.spring.boot.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "f2.tenant")
data class F2TrustedIssuersConfig (
    val issuerBaseUri: String
)
