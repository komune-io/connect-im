package io.komune.im.f2.user.domain.command

import f2.dsl.fnc.F2Function
import io.komune.im.core.user.domain.command.UserCoreDeleteCommand
import io.komune.im.core.user.domain.command.UserCoreDeletedEvent
import io.komune.im.core.user.domain.command.UserCoreDeleteCommandDTO
import io.komune.im.core.user.domain.command.UserCoreDeletedEventDTO
import kotlin.js.JsExport

/**
 * Permanently delete a user.
 * @d2 function
 * @parent [io.komune.im.f2.user.domain.D2UserPage]
 * @child [io.komune.im.core.user.domain.command.UserCoreDeleteCommandDTO]
 * @child [io.komune.im.core.user.domain.command.UserCoreDeletedEventDTO]
 * @order 80
 */
typealias UserDeleteFunction = F2Function<UserDeleteCommand, UserDeletedEvent>

@JsExport
interface UserDeleteCommandDTO: UserCoreDeleteCommandDTO

typealias UserDeleteCommand = UserCoreDeleteCommand

@JsExport
interface UserDeletedEventDTO: UserCoreDeletedEventDTO

typealias UserDeletedEvent = UserCoreDeletedEvent
