package city.smartb.im.api.config

import city.smartb.fs.s2.file.client.FileClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val FS_URL_PROPERTY = "fs.url"

@Configuration
@ConditionalOnProperty(FS_URL_PROPERTY)
class FsConfig {

    @Value("\${$FS_URL_PROPERTY}")
    lateinit var fsUrl: String

    @ConditionalOnMissingBean
    @Bean
    fun fsClient() = FileClient(
        url = fsUrl
    )
}
