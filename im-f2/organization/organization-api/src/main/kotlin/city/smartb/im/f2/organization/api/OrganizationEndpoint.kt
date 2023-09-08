package city.smartb.im.f2.organization.api

import city.smartb.im.commons.auth.policies.f2Function
import city.smartb.im.commons.utils.contentByteArray
import city.smartb.im.f2.organization.domain.command.OrganizationCreateFunction
import city.smartb.im.f2.organization.domain.command.OrganizationDeleteFunction
import city.smartb.im.f2.organization.domain.command.OrganizationDisableFunction
import city.smartb.im.f2.organization.domain.command.OrganizationUpdateFunction
import city.smartb.im.f2.organization.domain.command.OrganizationUploadLogoCommand
import city.smartb.im.f2.organization.domain.command.OrganizationUploadedLogoEvent
import city.smartb.im.f2.organization.domain.query.OrganizationGetFromInseeFunction
import city.smartb.im.f2.organization.domain.query.OrganizationGetFromInseeResult
import city.smartb.im.f2.organization.domain.query.OrganizationGetFunction
import city.smartb.im.f2.organization.domain.query.OrganizationGetResult
import city.smartb.im.f2.organization.domain.query.OrganizationPageFunction
import city.smartb.im.f2.organization.domain.query.OrganizationPageResult
import city.smartb.im.f2.organization.domain.query.OrganizationRefListFunction
import city.smartb.im.f2.organization.domain.query.OrganizationRefListResult
import city.smartb.im.f2.organization.lib.OrganizationAggregateService
import city.smartb.im.f2.organization.lib.OrganizationFinderService
import f2.dsl.cqrs.page.OffsetPagination
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
 * @parent [city.smartb.im.f2.organization.domain.D2OrganizationPage]
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
        organizationFinderService.getFromInsee(query.siret).let(::OrganizationGetFromInseeResult)
    }


    /**
     * Fetch a page of organizations.
     */
    @Bean
    fun organizationPage(): OrganizationPageFunction = f2Function { query ->
        logger.info("organizationPage: $query")
        organizationPoliciesEnforcer.checkPage()

        val roles = buildSet {
            query.roles?.let(::addAll)
            query.role?.let(::add)
        }.ifEmpty { null }

        organizationFinderService.page(
            search = query.search,
            roles = roles,
            attributes = query.attributes,
            withDisabled = query.withDisabled ?: false,
            offset = OffsetPagination(
                offset = query.offset ?: 0,
                limit = query.limit ?: Int.MAX_VALUE
            ),
        ).let {
            OrganizationPageResult(
                items = it.items,
                total = it.total
            )
        }
    }


    /**
     * Fetch all OrganizationRef.
     */
    @Bean
    fun organizationRefList(): OrganizationRefListFunction = f2Function { query ->
        logger.info("organizationRefList: $query")
        organizationPoliciesEnforcer.checkRefList()
        organizationFinderService.listRefs(query.withDisabled).let(::OrganizationRefListResult)
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
        organizationPoliciesEnforcer.checkDisable()
        organizationAggregateService.disable(command)
    }


    /**
     * Delete an organization and its users.
     */
    @Bean
    fun organizationDelete(): OrganizationDeleteFunction = f2Function { command ->
        logger.info("organizationDelete: $command")
        organizationPoliciesEnforcer.checkDelete()
        organizationAggregateService.delete(command)
    }

}
