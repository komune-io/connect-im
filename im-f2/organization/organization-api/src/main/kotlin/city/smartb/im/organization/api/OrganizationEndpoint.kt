package city.smartb.im.organization.api

import city.smartb.im.commons.auth.policies.f2Function
import city.smartb.im.commons.utils.contentByteArray
import city.smartb.im.organization.api.policies.OrganizationPoliciesEnforcer
import city.smartb.im.organization.domain.features.command.OrganizationCreateFunction
import city.smartb.im.organization.domain.features.command.OrganizationDeleteFunction
import city.smartb.im.organization.domain.features.command.OrganizationDisableFunction
import city.smartb.im.organization.domain.features.command.OrganizationUpdateFunction
import city.smartb.im.organization.domain.features.command.OrganizationUploadLogoCommand
import city.smartb.im.organization.domain.features.command.OrganizationUploadedLogoEvent
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeFunction
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeResult
import city.smartb.im.organization.domain.features.query.OrganizationGetFunction
import city.smartb.im.organization.domain.features.query.OrganizationGetResult
import city.smartb.im.organization.domain.features.query.OrganizationPageFunction
import city.smartb.im.organization.domain.features.query.OrganizationRefListFunction
import city.smartb.im.organization.domain.features.query.OrganizationRefListResult
import city.smartb.im.organization.lib.OrganizationAggregateService
import city.smartb.im.organization.lib.OrganizationFinderService
import org.springframework.context.annotation.Bean
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import s2.spring.utils.logger.Logger

/**
 * @d2 service
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 */
@RestController
@RequestMapping
@Service
class OrganizationEndpoint(
    private val organizationAggregateService: OrganizationAggregateService,
    private val organizationFinderService: OrganizationFinderService,
    private val organizationPoliciesEnforcer: OrganizationPoliciesEnforcer,
) {
    private val logger by Logger()

    /**
     * Fetch an Organization by its ID.
     */
    @Bean
    fun organizationGet(): OrganizationGetFunction = f2Function { query ->
        logger.info("organizationGet: $query")
        organizationPoliciesEnforcer.checkGet(query.id)
        organizationFinderService.getOrNull(query.id).let(::OrganizationGetResult)
    }

    /**
     * Fetch an Organization by its siret number from the Insee Sirene API.
     */
    @Bean
    fun organizationGetFromInsee(): OrganizationGetFromInseeFunction = f2Function { query ->
        logger.info("organizationGetFromInsee: $query")
        organizationPoliciesEnforcer.checkList()
        organizationFinderService.getFromInsee(query).let(::OrganizationGetFromInseeResult)
    }


    /**
     * Fetch a page of organizations.
     */
    @Bean
    fun organizationPage(): OrganizationPageFunction = f2Function { query ->
        logger.info("organizationPage: $query")
        organizationPoliciesEnforcer.checkPage()
        organizationFinderService.page(query)
    }


    /**
     * Fetch all OrganizationRef.
     */
    @Bean
    fun organizationRefList(): OrganizationRefListFunction = f2Function { query ->
        logger.info("organizationRefList: $query")
        organizationPoliciesEnforcer.checkRefList()
        organizationFinderService.listRefs(query).let(::OrganizationRefListResult)
    }

    /**
     * Create an organization.
     */
    @Bean
    fun organizationCreate(): OrganizationCreateFunction = f2Function { command ->
        logger.info("organizationCreate: $command")
        organizationPoliciesEnforcer.checkCreate()
        organizationAggregateService.create(command)
    }

    /**
     * Update an organization.
     */
    @Bean
    fun organizationUpdate(): OrganizationUpdateFunction = f2Function { command ->
        logger.info("organizationUpdate: $command")
        organizationPoliciesEnforcer.checkUpdate(command.id)
        organizationAggregateService.update(command)
    }

    /**
     * Upload a logo for a given organization
     */
    @PostMapping("/organizationUploadLogo")
    suspend fun organizationUploadLogo(
        @RequestPart("command") command: OrganizationUploadLogoCommand,
        @RequestPart("file") file: FilePart
    ): OrganizationUploadedLogoEvent {
        logger.info("organizationUploadLogo: $command")
        organizationPoliciesEnforcer.checkUpdate(command.id)
        return organizationAggregateService.uploadLogo(command, file.contentByteArray())
    }

    /**
     * Disable an organization and its users.
     */
    @Bean
    fun organizationDisable(): OrganizationDisableFunction = f2Function { command ->
        logger.info("organizationDisable: $command")
        organizationPoliciesEnforcer.checkDisable(command.id)
        organizationAggregateService.disable(command)
    }


    /**
     * Delete an organization and its users.
     */
    @Bean
    fun organizationDelete(): OrganizationDeleteFunction = f2Function { command ->
        logger.info("organizationDelete: $command")
        organizationPoliciesEnforcer.checkDelete(command.id)
        organizationAggregateService.delete(command)
    }

}
