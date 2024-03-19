package io.komune.im.script.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "im.script.space")
class ImScriptSpaceProperties(
    val auth: ImAuthProperties,
    val jsonCreate: String?,
    val jsonConfig: String?
)
