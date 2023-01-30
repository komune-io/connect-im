package city.smartb.im.api.config.properties

import city.smartb.i2.spring.boot.auth.config.I2TrustedIssuerProperties

data class I2IssuersProperties(
    override val name: String,
    override val authUrl: String,
    override val realm: String,
    val redirectUrl: String?,
    val im: I2IssuersClientProperties
): I2TrustedIssuerProperties(
    name = name,
    authUrl = authUrl,
    realm = realm
)

data class I2IssuersClientProperties(
    val clientId: String,
    val clientSecret: String,
)
