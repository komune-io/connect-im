package city.smartb.im.organization.api

import city.smartb.im.api.config.Roles
import city.smartb.im.organization.domain.features.command.OrganizationCreateFunction
import city.smartb.im.organization.domain.features.command.OrganizationDisableFunction
import city.smartb.im.organization.domain.features.command.OrganizationUpdateFunction
import city.smartb.im.organization.domain.features.command.OrganizationUploadLogoCommand
import city.smartb.im.organization.domain.features.command.OrganizationUploadedLogoEvent
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeFunction
import city.smartb.im.organization.domain.features.query.OrganizationGetFunction
import city.smartb.im.organization.domain.features.query.OrganizationPageFunction
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllFunction
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.OrganizationFeaturesImpl
import city.smartb.im.organization.lib.service.OrganizationAggregateService
import city.smartb.im.organization.lib.service.OrganizationFinderService
import javax.annotation.security.RolesAllowed
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
@Configuration
class OrganizationEndpoint(
    organizationFinderService: OrganizationFinderService<Organization>,
    organizationAggregateService: OrganizationAggregateService<Organization>,
) {

    private val organizationFeatures = OrganizationFeaturesImpl(
        organizationFinderService,
        organizationAggregateService,
        OrganizationMapperImpl()
    )

    /**
     * Fetch an Organization by its ID.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationGet(): OrganizationGetFunction<Organization> = organizationFeatures.organizationGet()

    /**
     * Fetch an Organization by its siret number from the Insee Sirene API.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationGetFromInsee(): OrganizationGetFromInseeFunction = organizationFeatures.organizationGetFromInsee()

    /**
     * Fetch a page of organizations.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationPage(): OrganizationPageFunction<Organization> = organizationFeatures.organizationPage()

    /**
     * Fetch all OrganizationRef.
     */
    @Bean
    @RolesAllowed(Roles.READ_ORGANIZATION)
    fun organizationRefGetAll(): OrganizationRefGetAllFunction = organizationFeatures.organizationRefGetAll()

    /**
     * Create an organization.
     */
    @Bean
    @RolesAllowed(city.smartb.i2.spring.boot.auth.SUPER_ADMIN_ROLE)
    fun organizationCreate(): OrganizationCreateFunction = organizationFeatures.organizationCreate()

    /**
     * Update an organization.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_ORGANIZATION)
    fun organizationUpdate(): OrganizationUpdateFunction = organizationFeatures.organizationUpdate()

    /**
     * Upload a logo for a given organization
     */
    @RolesAllowed(Roles.WRITE_ORGANIZATION)
    @PostMapping("/organizationUploadLogo")
    suspend fun organizationUploadLogo(
        @RequestPart("command") cmd: OrganizationUploadLogoCommand,
        @RequestPart("file") file: org.springframework.http.codec.multipart.FilePart
    ): OrganizationUploadedLogoEvent = organizationFeatures.organizationUploadLogo(cmd, file)

    /**
     * Disable an organization and its users.
     */
    @Bean
    @RolesAllowed(Roles.WRITE_ORGANIZATION)
    fun organizationDisable(): OrganizationDisableFunction = organizationFeatures.organizationDisable()
}
