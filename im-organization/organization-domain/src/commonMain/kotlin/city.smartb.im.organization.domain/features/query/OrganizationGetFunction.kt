package city.smartb.im.organization.domain.features.query

import city.smartb.im.commons.ImMessage
import city.smartb.im.organization.domain.model.OrganizationBase
import city.smartb.im.organization.domain.model.OrganizationId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Gets an organization by ID.
 * @d2 section
 * @parent [city.smartb.im.organization.domain.D2OrganizationQuerySection]
 */
typealias OrganizationGetFunction = F2Function<ImMessage<OrganizationGetQuery>, OrganizationGetResult>

/**
 * @d2 query
 * @parent [OrganizationGetFunction]
 */
data class OrganizationGetQuery(
    /**
     * Identifier of the organization.
     */
    val id: OrganizationId
): Command

/**
 * @d2 event
 * @parent [OrganizationGetFunction]
 */
data class OrganizationGetResult(
    /**
     * The organization.
     */
	val item: OrganizationBase?
): Event
