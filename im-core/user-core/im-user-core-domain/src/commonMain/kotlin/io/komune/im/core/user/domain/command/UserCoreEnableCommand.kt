package io.komune.im.core.user.domain.command

import io.komune.im.commons.model.UserId

data class UserCoreEnableCommand(
    val id: UserId
)

data class UserCoreEnabledEvent(
    val id: UserId
)
