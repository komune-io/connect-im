package io.komune.im.script.core.config

import io.komune.im.script.core.config.properties.ImRetryProperties
import io.komune.im.script.core.config.properties.ImScriptInitProperties
import io.komune.im.script.core.config.properties.ImScriptSpaceProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [
    ImRetryProperties::class,
    ImScriptSpaceProperties::class,
    ImScriptInitProperties::class
])
class AppScriptConfiguration
