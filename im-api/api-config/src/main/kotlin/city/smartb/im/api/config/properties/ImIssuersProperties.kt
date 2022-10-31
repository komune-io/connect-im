package city.smartb.im.api.config.properties

import city.smartb.i2.spring.boot.auth.config.I2TrustedIssuerProperties

data class ImIssuersProperties(
    override val name: String,
    override val authUrl: String,
    override val realm: String,
    val im: ImIssuersClientProperties
): I2TrustedIssuerProperties(
    name = name,
    authUrl = authUrl,
    realm = realm
)

data class ImIssuersClientProperties(
    val clientId: String,
    val clientSecret: String,
)
