package city.smartb.im.api.config.properties

import city.smartb.i2.spring.boot.auth.config.I2TrustedIssuerProperties

class ImIssuers(
    name: String,
    authUrl: String,
    realm: String,
    val im: ImIssuersProperties
): I2TrustedIssuerProperties(
    name = name,
    authUrl = authUrl,
    realm = realm
)

class ImIssuersProperties(
    val clientId: String,
    val clientSecret: String
)
