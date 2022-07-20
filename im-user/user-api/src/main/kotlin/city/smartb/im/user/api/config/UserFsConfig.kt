package city.smartb.im.user.api.config

import city.smartb.fs.s2.file.client.FileClient
import city.smartb.fs.s2.file.domain.features.command.FileInitPublicDirectoryCommand
import city.smartb.fs.s2.file.domain.model.FilePath
import city.smartb.im.api.config.FsConfig
import city.smartb.im.user.domain.model.UserId
import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnBean(FsConfig::class)
class UserFsConfig(
    private val fileClient: FileClient
) {
    companion object {
        const val objectType = "user"
        const val directory = "images"
        const val name = "logo.png"

        fun pathForUser(id: UserId) = FilePath(
            objectType = objectType,
            objectId = id,
            directory = directory,
            name = name
        )
    }

    init {
        runBlocking {
            val command = FileInitPublicDirectoryCommand(
                objectType = objectType,
                objectId = "*",
                directory = directory
            )
            fileClient.initPublicDirectory(listOf(command))
        }
    }
}
