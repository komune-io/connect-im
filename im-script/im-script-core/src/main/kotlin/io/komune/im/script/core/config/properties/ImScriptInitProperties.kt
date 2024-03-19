package io.komune.im.script.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("im.script.init")
class ImScriptInitProperties(
    val auth: ImAuthProperties,
    val json: String?,
)
