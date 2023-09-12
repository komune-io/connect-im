package city.smartb.im.f2.user.domain.command

import city.smartb.im.core.user.domain.command.UserCoreDeleteCommand
import city.smartb.im.core.user.domain.command.UserCoreDeletedEvent
import city.smartb.im.core.user.domain.command.UserDeleteCommandDTO
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport

/**
 * Permanently delete a user.
 * @d2 function
 * @parent [city.smartb.im.f2.user.domain.D2UserPage]
 * @child [city.smartb.im.core.user.domain.command.UserDeleteCommandDTO]
 * @child [city.smartb.im.core.user.domain.command.UserDeletedEventDTO]
 * @order 80
 */
typealias UserDeleteFunction = F2Function<UserDeleteCommand, UserDeletedEvent>

@JsExport
interface UserDeleteCommandDTO: UserDeleteCommandDTO, Command

typealias UserDeleteCommand = UserCoreDeleteCommand

@JsExport
interface UserDeletedEventDTO: city.smartb.im.core.user.domain.command.UserDeletedEventDTO, Event

typealias UserDeletedEvent = UserCoreDeletedEvent
