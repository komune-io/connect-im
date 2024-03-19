package io.komune.im.core.user.domain.command

import io.komune.im.commons.model.UserId

data class UserCoreDisableCommand(
    val id: UserId,
    val disabledBy: UserId
)

data class UserCoreDisabledEvent(
    val id: UserId
)
