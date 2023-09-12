package city.smartb.im.f2.organization.domain.command

import city.smartb.im.core.organization.domain.command.OrganizationCoreDeleteCommand
import city.smartb.im.core.organization.domain.command.OrganizationCoreDeletedEvent
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Delete an organization (but not its users).
 * @d2 function
 * @parent [city.smartb.im.f2.organization.domain.D2OrganizationPage]
 * @child [city.smartb.im.core.organization.domain.command.OrganizationDeleteCommandDTO]
 * @child [city.smartb.im.core.organization.domain.command.OrganizationDeletedEventDTO]
 * @order 50
 */
typealias OrganizationDeleteFunction = F2Function<OrganizationDeleteCommand, OrganizationDeletedEvent>

@JsExport
interface OrganizationDeleteCommandDTO: city.smartb.im.core.organization.domain.command.OrganizationDeleteCommandDTO

typealias OrganizationDeleteCommand = OrganizationCoreDeleteCommand

@JsExport
@JsName("OrganizationDeletedEventDTO")
interface OrganizationDeletedEventDTO: Event, city.smartb.im.core.organization.domain.command.OrganizationDeletedEventDTO

typealias OrganizationDeletedEvent = OrganizationCoreDeletedEvent
