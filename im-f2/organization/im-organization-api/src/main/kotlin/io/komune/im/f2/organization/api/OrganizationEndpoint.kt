package io.komune.im.f2.organization.api

import io.komune.im.commons.auth.policies.f2Function
import io.komune.im.commons.utils.contentByteArray
import io.komune.im.f2.organization.domain.command.OrganizationCreateFunction
import io.komune.im.f2.organization.domain.command.OrganizationDeleteFunction
import io.komune.im.f2.organization.domain.command.OrganizationDisableFunction
import io.komune.im.f2.organization.domain.command.OrganizationUpdateFunction
import io.komune.im.f2.organization.domain.command.OrganizationUploadLogoCommand
import io.komune.im.f2.organization.domain.command.OrganizationUploadedLogoEvent
import io.komune.im.f2.organization.domain.model.OrganizationStatus
import io.komune.im.f2.organization.domain.query.OrganizationGetFromInseeFunction
import io.komune.im.f2.organization.domain.query.OrganizationGetFromInseeResult
import io.komune.im.f2.organization.domain.query.OrganizationGetFunction
import io.komune.im.f2.organization.domain.query.OrganizationGetResult
import io.komune.im.f2.organization.domain.query.OrganizationPageFunction
import io.komune.im.f2.organization.domain.query.OrganizationPageResult
import io.komune.im.f2.organization.domain.query.OrganizationRefListFunction
import io.komune.im.f2.organization.domain.query.OrganizationRefListResult
import io.komune.im.f2.organization.lib.OrganizationAggregateService
import io.komune.im.f2.organization.lib.OrganizationFinderService
import f2.dsl.cqrs.page.OffsetPagination
import io.komune.im.f2.organization.domain.OrganizationApi
import io.komune.im.f2.organization.domain.query.OrganizationRefGetFunction
import io.komune.im.f2.organization.domain.query.OrganizationRefGetResult
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

/**
 * @d2 service
 * @parent [io.komune.im.f2.organization.domain.D2OrganizationPage]
 */
@RestController
@RequestMapping
@Service
class OrganizationEndpoint(
    private val organizationAggregateService: OrganizationAggregateService,
    private val organizationFinderService: OrganizationFinderService,
    private val organizationPoliciesEnforcer: OrganizationPoliciesEnforcer,
): OrganizationApi {
    private val logger = LoggerFactory.getLogger(OrganizationEndpoint::class.java)

    /**
     * Fetch an Organization by its ID.
     */
    @Bean
    override fun organizationGet(): OrganizationGetFunction = f2Function { query ->
        logger.info("organizationGet: $query")
        organizationPoliciesEnforcer.checkGet(query.id)
        organizationFinderService.getOrNull(query.id).let(::OrganizationGetResult)
    }

    @Bean
    override fun organizationRefGet(): OrganizationRefGetFunction = f2Function { query ->
        logger.info("organizationRefGet: $query")
        organizationPoliciesEnforcer.checkGet(query.id)
        organizationFinderService.getRefOrNull(query.id).let(::OrganizationRefGetResult)
    }

    /**
     * Fetch an Organization by its siret number from the Insee Sirene API.
     */
    @Bean
    override fun organizationGetFromInsee(): OrganizationGetFromInseeFunction = f2Function { query ->
        logger.info("organizationGetFromInsee: $query")
        organizationPoliciesEnforcer.checkList()
        organizationFinderService.getFromInsee(query.siret).let(::OrganizationGetFromInseeResult)
    }


    /**
     * Fetch a page of organizations.
     */
    @Bean
    override fun organizationPage(): OrganizationPageFunction = f2Function { query ->
        logger.info("organizationPage: $query")
        organizationPoliciesEnforcer.checkPage()

        val roles = buildSet {
            query.roles?.let(::addAll)
            query.role?.let(::add)
        }.ifEmpty { null }

        organizationFinderService.page(
            name = query.name,
            roles = roles,
            attributes = query.attributes,
            status = query.status?.map(OrganizationStatus::valueOf),
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
    override fun organizationRefList(): OrganizationRefListFunction = f2Function { query ->
        logger.info("organizationRefList: $query")
        organizationPoliciesEnforcer.checkRefList()
        organizationFinderService.listRefs(query.withDisabled).let(::OrganizationRefListResult)
    }

    /**
     * Create an organization.
     */
    @Bean
    override fun organizationCreate(): OrganizationCreateFunction = f2Function { command ->
        logger.info("organizationCreate: $command")
        organizationPoliciesEnforcer.checkCreate()
        organizationAggregateService.create(
            organizationPoliciesEnforcer.enforceCommand(command)
        )
    }

    /**
     * Update an organization.
     */
    @Bean
    override fun organizationUpdate(): OrganizationUpdateFunction = f2Function { command ->
        logger.info("organizationUpdate: $command")
        organizationPoliciesEnforcer.checkUpdate(command.id)
        organizationAggregateService.update(
            organizationPoliciesEnforcer.enforceCommand(command)
        )
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
    override fun organizationDisable(): OrganizationDisableFunction = f2Function { command ->
        logger.info("organizationDisable: $command")
        organizationPoliciesEnforcer.checkDisable()
        organizationAggregateService.disable(command)
    }


    /**
     * Delete an organization and its users.
     */
    @Bean
    override fun organizationDelete(): OrganizationDeleteFunction = f2Function { command ->
        logger.info("organizationDelete: $command")
        organizationPoliciesEnforcer.checkDelete()
        organizationAggregateService.delete(command)
    }

}
