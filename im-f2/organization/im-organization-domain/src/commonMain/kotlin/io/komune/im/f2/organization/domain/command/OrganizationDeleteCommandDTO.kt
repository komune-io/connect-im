package io.komune.im.f2.organization.domain.command

import f2.dsl.fnc.F2Function
import io.komune.im.core.organization.domain.command.OrganizationCoreDeleteCommand
import io.komune.im.core.organization.domain.command.OrganizationCoreDeletedEvent
import io.komune.im.core.organization.domain.command.OrganizationCoreDeleteCommandDTO
import io.komune.im.core.organization.domain.command.OrganizationCoreDeletedEventDTO
import kotlin.js.JsExport

/**
 * Delete an organization (but not its users).
 * @d2 function
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
 * @child [io.komune.im.core.organization.domain.command.OrganizationCoreDeleteCommandDTO]
 * @child [io.komune.im.core.organization.domain.command.OrganizationCoreDeletedEventDTO]
 * @order 50
 */
typealias OrganizationDeleteFunction = F2Function<OrganizationDeleteCommand, OrganizationDeletedEvent>

@JsExport
interface OrganizationDeleteCommandDTO: OrganizationCoreDeleteCommandDTO

typealias OrganizationDeleteCommand = OrganizationCoreDeleteCommand

@JsExport
interface OrganizationDeletedEventDTO: OrganizationCoreDeletedEventDTO

typealias OrganizationDeletedEvent = OrganizationCoreDeletedEvent
