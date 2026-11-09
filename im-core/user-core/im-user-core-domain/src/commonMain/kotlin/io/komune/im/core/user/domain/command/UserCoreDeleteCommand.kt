package io.komune.im.core.user.domain.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import io.komune.im.commons.model.UserId
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * @d2 command
 */
@JsExport
interface UserCoreDeleteCommandDTO: Command {
    /**
     * Id of the user to delete.
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@JsExport
@Serializable
data class UserCoreDeleteCommand(
    override val id: UserId
): UserCoreDeleteCommandDTO

/**
 * @d2 event
 */
@JsExport
interface UserCoreDeletedEventDTO: Event {
    /**
     * Id of the deleted user.
     */
    val id: UserId
}

@JsExport
@Serializable
data class UserCoreDeletedEvent(
    override val id: UserId
): UserCoreDeletedEventDTO
