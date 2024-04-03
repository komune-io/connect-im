package io.komune.im.f2.user.domain.command

import io.komune.im.core.user.domain.command.UserCoreDeleteCommand
import io.komune.im.core.user.domain.command.UserCoreDeletedEvent
import io.komune.im.core.user.domain.command.UserDeleteCommandDTO
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.core.user.domain.command.UserDeletedEventDTO
import kotlin.js.JsExport

/**
 * Permanently delete a user.
 * @d2 function
 * @parent [io.komune.im.f2.user.domain.D2UserPage]
 * @child [io.komune.im.core.user.domain.command.UserDeleteCommandDTO]
 * @child [io.komune.im.core.user.domain.command.UserDeletedEventDTO]
 * @order 80
 */
typealias UserDeleteFunction = F2Function<UserDeleteCommand, UserDeletedEvent>

@JsExport
interface UserDeleteCommandDTO: UserDeleteCommandDTO, Command

typealias UserDeleteCommand = UserCoreDeleteCommand

@JsExport
interface UserDeletedEventDTO: UserDeletedEventDTO, Event

typealias UserDeletedEvent = UserCoreDeletedEvent
