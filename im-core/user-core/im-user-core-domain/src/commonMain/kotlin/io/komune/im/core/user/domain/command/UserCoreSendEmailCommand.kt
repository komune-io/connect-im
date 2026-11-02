package io.komune.im.core.user.domain.command

import io.komune.im.commons.model.UserId

data class UserCoreSendEmailCommand(
    val id: UserId,
    val actions: Collection<String>,
    val reason: UserCoreEmailReason = UserCoreEmailReason.ACCOUNT_ACTION,
)

enum class UserCoreEmailReason {
    ACCOUNT_ACTION,
    WELCOME,
}

data class UserCoreSentEmailEvent(
    val id: UserId,
    val actions: Collection<String>
)
