package city.smartb.im.core.user.domain.command

import city.smartb.im.commons.model.UserId

data class UserSendEmailCommand(
    val id: UserId,
    val actions: Collection<String>,
)

data class UserSentEmailEvent(
    val id: UserId,
    val actions: Collection<String>
)
