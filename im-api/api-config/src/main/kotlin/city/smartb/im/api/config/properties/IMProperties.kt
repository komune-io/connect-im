package city.smartb.im.api.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "im")
data class IMProperties (
    val organization: OrganizationProperties?,
    val user: UserProperties?
)
class OrganizationProperties(
    val insee: InseeProperties?
)
class UserProperties(
    val actions: UserActionProperties?
)

class UserActionProperties(
    val useJwtClientID: Boolean? = false
)



class InseeProperties(
    val sireneApi: String,
    val token: String
)
