package city.smartb.im.api.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "connect.im")
data class IMProperties (
    val organization: OrganizationProperties?,
    val smtp: Map<String, String>? = null,
    val theme: String? = null,
    val user: UserProperties?
)
class OrganizationProperties(
    val insee: InseeProperties?
)
class UserProperties(
    val action: UserActionProperties?
)

class UserActionProperties(
    val useJwtClientId: Boolean? = false
)

class InseeProperties(
    val sireneApi: String,
    val token: String
)
