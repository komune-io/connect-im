package city.smartb.im.f2.user.domain.command

import city.smartb.im.core.user.domain.command.UserDeleteCommand
import city.smartb.im.core.user.domain.command.UserDeletedEvent
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
typealias UserDeleteFunction = F2Function<UserDeleteCommandDTOBase, UserDeletedEventDTOBase>

@JsExport
interface UserDeleteCommandDTO: city.smartb.im.core.user.domain.command.UserDeleteCommandDTO, Command

typealias UserDeleteCommandDTOBase = UserDeleteCommand

@JsExport
interface UserDeletedEventDTO: city.smartb.im.core.user.domain.command.UserDeletedEventDTO, Event

typealias UserDeletedEventDTOBase = UserDeletedEvent
