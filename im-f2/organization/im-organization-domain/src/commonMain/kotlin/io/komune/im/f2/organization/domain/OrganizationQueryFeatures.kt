package io.komune.im.f2.organization.domain

import io.komune.im.f2.organization.domain.query.OrganizationGetFromInseeFunction
import io.komune.im.f2.organization.domain.query.OrganizationGetFunction
import io.komune.im.f2.organization.domain.query.OrganizationPageFunction
import io.komune.im.f2.organization.domain.query.OrganizationRefGetFunction
import io.komune.im.f2.organization.domain.query.OrganizationRefListFunction

interface OrganizationQueryFeatures {

    /**
     * Fetch an Organization by its ID.
     */
    fun organizationGet(): OrganizationGetFunction

    /**
     * Fetch an Organization Ref by its ID.
     */
    fun organizationRefGet(): OrganizationRefGetFunction

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
