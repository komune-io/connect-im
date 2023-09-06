package city.smartb.im.api.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

const val FS_URL_PROPERTY = "connect.fs"
@ConfigurationProperties(prefix = FS_URL_PROPERTY)
data class FsProperties (
    val url: String,
)
