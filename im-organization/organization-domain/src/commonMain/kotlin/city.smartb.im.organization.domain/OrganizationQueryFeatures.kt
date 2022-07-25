package city.smartb.im.organization.domain

import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeFunction
import city.smartb.im.organization.domain.features.query.OrganizationGetFunction
import city.smartb.im.organization.domain.features.query.OrganizationPageFunction
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllFunction
import city.smartb.im.organization.domain.model.OrganizationDTO

interface OrganizationQueryFeatures<MODEL: OrganizationDTO> {

    /**
     * Fetch an Organization by its ID.
     */
    fun organizationGet(): OrganizationGetFunction<MODEL>

    /**
     * Fetch an Organization by its siret number from the Insee Sirene API.
     */
    fun organizationGetFromInsee(): OrganizationGetFromInseeFunction

    /**
     * Fetch a page of organizations.
     */
    fun organizationPage(): OrganizationPageFunction<MODEL>

    /**
     * Fetch all OrganizationRef.
     */
    fun organizationRefGetAll(): OrganizationRefGetAllFunction

}
