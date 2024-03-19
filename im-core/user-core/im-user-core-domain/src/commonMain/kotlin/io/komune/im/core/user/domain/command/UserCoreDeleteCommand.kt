package io.komune.im.core.user.domain.command

import io.komune.im.commons.model.UserId
import kotlin.js.JsExport

/**
 * @d2 command
 */
@JsExport
interface UserDeleteCommandDTO {
    /**
     * Id of the user to delete.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
data class UserCoreDeleteCommand(
    override val id: UserId
): UserDeleteCommandDTO

/**
 * @d2 event
 */
@JsExport
interface UserDeletedEventDTO {
    /**
     * Id of the deleted user.
     */
    val id: UserId
}

data class UserCoreDeletedEvent(
    override val id: UserId
): UserDeletedEventDTO
