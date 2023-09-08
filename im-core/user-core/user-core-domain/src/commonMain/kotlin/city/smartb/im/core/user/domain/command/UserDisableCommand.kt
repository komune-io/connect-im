package city.smartb.im.core.user.domain.command

import city.smartb.im.commons.model.UserId

data class UserDisableCommand(
    val id: UserId,
    val disabledBy: UserId
)

data class UserDisabledEvent(
    val id: UserId
)
