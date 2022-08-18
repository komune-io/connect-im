package city.smartb.im.api.config.properties

import city.smartb.i2.spring.boot.auth.config.I2TrustedIssuerProperties

class ImIssuersProperties(
    name: String,
    authUrl: String,
    realm: String,
    val im: ImIssuersClientProperties
): I2TrustedIssuerProperties(
    name = name,
    authUrl = authUrl,
    realm = realm
)

class ImIssuersClientProperties(
    val clientId: String,
    val clientSecret: String,
    val redirectUrl: String?
)
