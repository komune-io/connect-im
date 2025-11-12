package io.komune.im.f2.user.domain.command

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.UserId
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

typealias UserEnableFunction = F2Function<UserEnableCommand, UserEnabledEvent>

/**
 * Enable a user.
 * @d2 command
 * @parent [io.komune.im.f2.user.domain.D2UserF2Page]
 */
@JsExport
@JsName("UserEnableCommandDTO")
interface UserEnableCommandDTO {
    /**
     * User id to enable
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserEnableCommand(
    override val id: UserId
) : UserEnableCommandDTO

/**
 * @d2 event
 * @parent [io.komune.im.f2.user.domain.D2UserF2Page]
 */
@JsExport
@JsName("UserEnabledEventDTO")
interface UserEnabledEventDTO : Event {
    /**
     * User id enabled
     */
    val id: UserId
}

/**
 * @d2 inherit
 */
@Serializable
data class UserEnabledEvent(
    override val id: UserId
) : UserEnabledEventDTO
