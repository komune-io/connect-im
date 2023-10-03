package city.smartb.im.f2.organization.domain

import city.smartb.im.f2.organization.domain.model.OrganizationDTO
import city.smartb.im.f2.organization.domain.query.OrganizationGetFromInseeFunction
import city.smartb.im.f2.organization.domain.query.OrganizationGetFunction
import city.smartb.im.f2.organization.domain.query.OrganizationPageFunction
import city.smartb.im.f2.organization.domain.query.OrganizationRefListFunction

interface OrganizationQueryFeatures<MODEL: OrganizationDTO> {

    /**
     * Fetch an Organization by its ID.
     */
    fun organizationGet(): OrganizationGetFunction

    /**
     * Fetch an Organization by its siret number from the Insee Sirene API.
     */
    fun organizationGetFromInsee(): OrganizationGetFromInseeFunction

    /**
     * Fetch a page of organizations.
     */
    fun organizationPage(): OrganizationPageFunction

    /**
     * Fetch all OrganizationRef.
     */
    fun organizationRefList(): OrganizationRefListFunction

}
