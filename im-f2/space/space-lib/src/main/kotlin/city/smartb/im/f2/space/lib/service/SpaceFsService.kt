package city.smartb.im.f2.space.lib.service

import city.smartb.fs.s2.file.client.FileClient
import city.smartb.im.api.config.FsConfig
import city.smartb.im.f2.space.domain.model.SpaceIdentifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnBean(FsConfig::class)
class SpaceFsService(
    private val fileClient: FileClient
) {
    companion object {
        fun createBucket(id: SpaceIdentifier) {
            TODO()
        }

    }
}
