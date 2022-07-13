package city.smartb.im.api.config

import city.smartb.fs.s2.file.client.FileClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FsConfig {

    @Value("\${fs.url}")
    lateinit var fsUrl: String

    @Bean
    fun fsClient() = FileClient(
        url = fsUrl
    )
}
