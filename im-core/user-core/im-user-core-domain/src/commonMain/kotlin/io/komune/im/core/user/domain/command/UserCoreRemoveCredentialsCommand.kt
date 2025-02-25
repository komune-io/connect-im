package io.komune.im.core.user.domain.command

import io.komune.im.commons.model.UserId

data class UserCoreRemoveCredentialsCommand(
    val id: UserId,
    val type: CredentialType
)

data class UserCoreRemovedCredentialsEvent(
    val id: UserId
)

enum class CredentialType(val value: String) {
    OTP("otp"),
}
