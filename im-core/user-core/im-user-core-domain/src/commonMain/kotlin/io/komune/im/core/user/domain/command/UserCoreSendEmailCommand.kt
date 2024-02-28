package io.komune.im.core.user.domain.command

import io.komune.im.commons.model.UserId

data class UserCoreSendEmailCommand(
    val id: UserId,
    val actions: Collection<String>,
)

data class UserCoreSentEmailEvent(
    val id: UserId,
    val actions: Collection<String>
)
