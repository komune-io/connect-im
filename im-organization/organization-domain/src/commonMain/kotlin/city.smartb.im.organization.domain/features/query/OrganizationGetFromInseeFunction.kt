package city.smartb.im.organization.domain.features.query

import city.smartb.im.organization.domain.model.Organization
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Get an organization by Siret from the Insee Sirene API.
 * @d2 function
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 20
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
 * @d2 result
 * @parent [OrganizationGetFromInseeFunction]
 */
data class OrganizationGetFromInseeResult(
    /**
     * The organization.
     */
    val item: Organization?
): Event
