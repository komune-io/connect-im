package city.smartb.im.organization.api

import city.smartb.i2.spring.boot.auth.PermissionEvaluator
import city.smartb.i2.spring.boot.auth.SUPER_ADMIN_ROLE
import city.smartb.im.api.config.Roles
import city.smartb.im.organization.api.service.OrganizationAggregateService
import city.smartb.im.organization.api.service.OrganizationFinderService
import city.smartb.im.organization.domain.features.command.OrganizationCreateFunction
import city.smartb.im.organization.domain.features.command.OrganizationUpdateFunction
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeFunction
import city.smartb.im.organization.domain.features.query.OrganizationGetFunction
import city.smartb.im.organization.domain.features.query.OrganizationPageFunction
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllFunction
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger
import javax.annotation.security.RolesAllowed

/**
 * @d2 service
 * @title Organization/Entrypoints
 */
@Configuration
class OrganizationEndpoint(
    private val organizationFinderService: OrganizationFinderService,
    private val organizationAggregateService: OrganizationAggregateService,
    private val permissionEvaluator: PermissionEvaluator
) {
    private val logger by Logger()

    /**
     * Fetches an Organization by its ID.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationGet(): OrganizationGetFunction = f2Function { query ->
        logger.info("organizationGet: $query")
        organizationFinderService.organizationGet(query)
    }

    /**
     * Fetches an Organization by its siret number.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationGetFromInsee(): OrganizationGetFromInseeFunction = f2Function { query ->
        logger.info("organizationGetFromInsee: $query")
        organizationFinderService.organizationGetFromInsee(query)
    }

    /**
     * Fetches a page of organizations.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationPage(): OrganizationPageFunction = f2Function { query ->
        logger.info("organizationPage: $query")
        organizationFinderService.organizationPage(query)
    }

    /**
     * Fetches all OrganizationRef.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationRefGetAll(): OrganizationRefGetAllFunction = f2Function { query ->
        logger.info("organizationRefGetAll: $query")
        organizationFinderService.organizationRefGetAll(query)
    }

    /**
     * Creates an Organization.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun organizationCreate(): OrganizationCreateFunction = f2Function { cmd ->
        logger.info("organizationCreate: $cmd")
        organizationAggregateService.organizationCreate(cmd)
    }

    /**
     * Updates an Organization.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_ORGANIZATION)
    fun organizationUpdate(): OrganizationUpdateFunction = f2Function { cmd ->
        logger.info("organizationUpdate: $cmd")
        organizationAggregateService.organizationUpdate(cmd)
    }
}
