package city.smartb.im.organization.api

import city.smartb.i2.spring.boot.auth.SUPER_ADMIN_ROLE
import city.smartb.im.api.config.Roles
import city.smartb.im.commons.utils.contentByteArray
import city.smartb.im.organization.api.service.OrganizationAggregateService
import city.smartb.im.organization.api.service.OrganizationFinderService
import city.smartb.im.organization.domain.features.command.OrganizationCreateFunction
import city.smartb.im.organization.domain.features.command.OrganizationDisableFunction
import city.smartb.im.organization.domain.features.command.OrganizationUpdateFunction
import city.smartb.im.organization.domain.features.command.OrganizationUploadLogoCommand
import city.smartb.im.organization.domain.features.command.OrganizationUploadedLogoEvent
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeFunction
import city.smartb.im.organization.domain.features.query.OrganizationGetFunction
import city.smartb.im.organization.domain.features.query.OrganizationPageFunction
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllFunction
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import s2.spring.utils.logger.Logger
import javax.annotation.security.RolesAllowed

/**
 * @d2 service
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 */
@RestController
@RequestMapping
@Configuration
class OrganizationEndpoint(
    private val organizationFinderService: OrganizationFinderService,
    private val organizationAggregateService: OrganizationAggregateService
) {
    private val logger by Logger()

    /**
     * Fetch an Organization by its ID.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationGet(): OrganizationGetFunction = f2Function { query ->
        logger.info("organizationGet: $query")
        organizationFinderService.organizationGet(query)
    }

    /**
     * Fetch an Organization by its siret number from the Insee Sirene API.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationGetFromInsee(): OrganizationGetFromInseeFunction = f2Function { query ->
        logger.info("organizationGetFromInsee: $query")
        organizationFinderService.organizationGetFromInsee(query)
    }

    /**
     * Fetch a page of organizations.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationPage(): OrganizationPageFunction = f2Function { query ->
        logger.info("organizationPage: $query")
        organizationFinderService.organizationPage(query)
    }

    /**
     * Fetch all OrganizationRef.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationRefGetAll(): OrganizationRefGetAllFunction = f2Function { query ->
        logger.info("organizationRefGetAll: $query")
        organizationFinderService.organizationRefGetAll(query)
    }

    /**
     * Create an organization.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun organizationCreate(): OrganizationCreateFunction = f2Function { cmd ->
        logger.info("organizationCreate: $cmd")
        organizationAggregateService.create(cmd)
    }

    /**
     * Update an organization.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_ORGANIZATION)
    fun organizationUpdate(): OrganizationUpdateFunction = f2Function { cmd ->
        logger.info("organizationUpdate: $cmd")
        organizationAggregateService.update(cmd)
    }

    /**
     * Upload a logo for a given organization
     */
    @RolesAllowed(Roles.WRITE_ORGANIZATION)
    @PostMapping("/organizationUploadLogo")
    suspend fun organizationUploadLogo(
        @RequestPart("command") cmd: OrganizationUploadLogoCommand,
        @RequestPart("file") file: FilePart
    ): OrganizationUploadedLogoEvent {
        logger.info("organizationUploadLogo: $cmd")
        return organizationAggregateService.uploadLogo(cmd, file.contentByteArray())
    }

    /**
     * Disable an organization and its users.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_ORGANIZATION)
    fun organizationDisable(): OrganizationDisableFunction = f2Function { cmd ->
        logger.info("organizationDisable: $cmd")
        organizationAggregateService.disable(cmd)
    }
}
