package city.smartb.im.core.user.domain.command

import city.smartb.im.commons.model.UserId

data class UserCoreSendEmailCommand(
    val id: UserId,
    val actions: Collection<String>,
)

data class UserCoreSentEmailEvent(
    val id: UserId,
    val actions: Collection<String>
)
