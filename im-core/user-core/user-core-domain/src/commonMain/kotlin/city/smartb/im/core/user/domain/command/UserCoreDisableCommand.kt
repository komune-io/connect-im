package city.smartb.im.core.user.domain.command

import city.smartb.im.commons.model.UserId

data class UserCoreDisableCommand(
    val id: UserId,
    val disabledBy: UserId
)

data class UserCoreDisabledEvent(
    val id: UserId
)
