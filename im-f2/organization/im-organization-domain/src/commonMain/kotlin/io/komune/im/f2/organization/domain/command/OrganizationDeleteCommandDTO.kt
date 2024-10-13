package io.komune.im.f2.organization.domain.command

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import io.komune.im.core.organization.domain.command.OrganizationCoreDeleteCommand
import io.komune.im.core.organization.domain.command.OrganizationCoreDeletedEvent
import io.komune.im.core.organization.domain.command.OrganizationDeleteCommandDTO
import io.komune.im.core.organization.domain.command.OrganizationDeletedEventDTO
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Delete an organization (but not its users).
 * @d2 function
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
 * @child [io.komune.im.core.organization.domain.command.OrganizationDeleteCommandDTO]
 * @child [io.komune.im.core.organization.domain.command.OrganizationDeletedEventDTO]
 * @order 50
 */
typealias OrganizationDeleteFunction = F2Function<OrganizationDeleteCommand, OrganizationDeletedEvent>

@JsExport
interface OrganizationDeleteCommandDTO: OrganizationDeleteCommandDTO

typealias OrganizationDeleteCommand = OrganizationCoreDeleteCommand

@JsExport
@JsName("OrganizationDeletedEventDTO")
interface OrganizationDeletedEventDTO: Event, OrganizationDeletedEventDTO

typealias OrganizationDeletedEvent = OrganizationCoreDeletedEvent
