package city.smartb.im.space.lib.service

import city.smartb.fs.s2.file.client.FileClient
import city.smartb.im.api.config.FsConfig
import city.smartb.im.space.domain.model.SpaceId
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnBean(FsConfig::class)
class SpaceFsService(
    private val fileClient: FileClient
) {
    companion object {
        fun createBucket(id: SpaceId) {
            TODO()
        }

    }
}
