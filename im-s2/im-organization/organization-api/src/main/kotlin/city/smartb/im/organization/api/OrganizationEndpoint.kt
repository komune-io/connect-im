package city.smartb.im.organization.api

import city.smartb.im.commons.auth.policies.verify
import city.smartb.im.organization.api.policies.OrganizationPoliciesEnforcer
import city.smartb.im.organization.domain.features.command.OrganizationCreateFunction
import city.smartb.im.organization.domain.features.command.OrganizationDeleteFunction
import city.smartb.im.organization.domain.features.command.OrganizationDisableFunction
import city.smartb.im.organization.domain.features.command.OrganizationUpdateFunction
import city.smartb.im.organization.domain.features.command.OrganizationUploadLogoCommand
import city.smartb.im.organization.domain.features.command.OrganizationUploadedLogoEvent
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeFunction
import city.smartb.im.organization.domain.features.query.OrganizationGetFunction
import city.smartb.im.organization.domain.features.query.OrganizationPageFunction
import city.smartb.im.organization.domain.features.query.OrganizationRefListFunction
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.OrganizationFeaturesImpl
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

/**
 * @d2 service
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 */
@RestController
@RequestMapping
@Service
class OrganizationEndpoint(
    private val organizationFeatures: OrganizationFeaturesImpl<Organization>,
    private val organizationPoliciesEnforcer: OrganizationPoliciesEnforcer,
) {

    /**
     * Fetch an Organization by its ID.
     */
    @Bean
    fun organizationGet(): OrganizationGetFunction<Organization> = verify(organizationFeatures.organizationGet()) { query ->
        organizationPoliciesEnforcer.checkGet(query.id)
    }

    /**
     * Fetch an Organization by its siret number from the Insee Sirene API.
     */
    @Bean
    fun organizationGetFromInsee(): OrganizationGetFromInseeFunction = verify(organizationFeatures.organizationGetFromInsee()) { query ->
        organizationPoliciesEnforcer.checkList()
    }


    /**
     * Fetch a page of organizations.
     */
    @Bean
    fun organizationPage(): OrganizationPageFunction<Organization> = verify(organizationFeatures.organizationPage()) { query ->
        organizationPoliciesEnforcer.checkPage()
    }


    /**
     * Fetch all OrganizationRef.
     */
    @Bean
    fun organizationRefList(): OrganizationRefListFunction = verify(organizationFeatures.organizationRefList()) { query ->
        organizationPoliciesEnforcer.checkRefList()
    }

    /**
     * Create an organization.
     */
    @Bean
    fun organizationCreate(): OrganizationCreateFunction = verify(organizationFeatures.organizationCreate()) { command ->
        organizationPoliciesEnforcer.checkCreate()
    }

    /**
     * Update an organization.
     */
    @Bean
    fun organizationUpdate(): OrganizationUpdateFunction = verify(organizationFeatures.organizationUpdate()) { command ->
        organizationPoliciesEnforcer.checkUpdate(command.id)
    }

    /**
     * Upload a logo for a given organization
     */
    @PostMapping("/organizationUploadLogo")
    suspend fun organizationUploadLogo(
        @RequestPart("command") cmd: OrganizationUploadLogoCommand,
        @RequestPart("file") file: org.springframework.http.codec.multipart.FilePart
    ): OrganizationUploadedLogoEvent = organizationFeatures.organizationUploadLogo(cmd, file)

    /**
     * Disable an organization and its users.
     */
    @Bean
    fun organizationDisable(): OrganizationDisableFunction = verify(organizationFeatures.organizationDisable()) { command ->
        organizationPoliciesEnforcer.checkDisable(command.id)
    }


        /**
     * Delete an organization and its users.
     */
    @Bean
    fun organizationDelete(): OrganizationDeleteFunction = verify(organizationFeatures.organizationDelete()) { command ->
        organizationPoliciesEnforcer.checkDelete(command.id)
    }

}
