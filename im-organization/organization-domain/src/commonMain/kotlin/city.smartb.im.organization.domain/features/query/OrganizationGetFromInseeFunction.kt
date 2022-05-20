package city.smartb.im.organization.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import city.smartb.im.organization.domain.model.OrganizationBase

/**
 * Gets an organization by Siret.
 * @d2 section
 * @parent [city.smartb.im.organization.domain.D2OrganizationQuerySection]
 */
typealias OrganizationGetFromInseeFunction =
        F2Function<OrganizationGetFromInseeQuery, OrganizationGetFromInseeResult>

/**
 * @d2 query
 * @parent [OrganizationGetFromInseeFunction]
 */
data class OrganizationGetFromInseeQuery(
    /**
     * Siret number of the organization.
     * @example [city.smartb.im.organization.domain.model.Organization.siret]
     */
    val siret: String
): Command

/**
 * @d2 event
 * @parent [OrganizationGetFromInseeFunction]
 */
data class OrganizationGetFromInseeResult(
    /**
     * The organization.
     */
    val item: OrganizationBase?
): Event
