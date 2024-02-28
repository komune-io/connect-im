package io.komune.im.api.config

import city.smartb.fs.s2.file.client.FileClient
import io.komune.im.api.config.properties.FS_URL_PROPERTY
import io.komune.im.api.config.properties.FsProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration



@Configuration
@ConditionalOnProperty(FS_URL_PROPERTY)
@EnableConfigurationProperties(FsProperties::class)
class FsConfig {

    @ConditionalOnMissingBean
    @Bean
    fun fsClient(fsProperties: FsProperties) = FileClient(
        url = fsProperties.url
    )
}
