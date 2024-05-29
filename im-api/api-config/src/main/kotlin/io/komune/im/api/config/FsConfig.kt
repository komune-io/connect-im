package io.komune.im.api.config

import io.komune.fs.s2.file.client.FileClient
import io.komune.im.api.config.bean.ImAuthenticationProvider
import io.komune.im.api.config.properties.FS_URL_PROPERTY
import io.komune.im.api.config.properties.FsProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(FS_URL_PROPERTY)
@EnableConfigurationProperties(FsProperties::class)
class FsConfig {

    private val logguer = LoggerFactory.getLogger(FsConfig::class.java)

    @ConditionalOnMissingBean
    @Bean
    fun fsClient(fsProperties: FsProperties, im: ImAuthenticationProvider) = FileClient(
        url = fsProperties.url,
        authProvider = {
            logguer.info("""
                FS - Loggin with space[${im.getAuth().space}]
                     Realm[${im.getAuth().master.realmId}]
                     ClientId[${im.getAuth().master.clientId}]
             """.trimIndent())
            im.getAuth().master
        }
    )
}
