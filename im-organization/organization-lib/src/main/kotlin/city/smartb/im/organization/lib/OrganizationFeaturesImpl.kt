package city.smartb.im.organization.lib

import city.smartb.im.commons.utils.contentByteArray
import city.smartb.im.organization.domain.OrganizationCommandFeatures
import city.smartb.im.organization.domain.OrganizationQueryFeatures
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
import city.smartb.im.organization.domain.model.OrganizationDTO
import city.smartb.im.organization.lib.service.OrganizationAggregateService
import city.smartb.im.organization.lib.service.OrganizationFinderService
import city.smartb.im.organization.lib.service.OrganizationMapper
import f2.dsl.fnc.f2Function

class OrganizationFeaturesImpl<MODEL: OrganizationDTO>(
    private val organizationFinderService: OrganizationFinderService<MODEL>,
    private val organizationAggregateService: OrganizationAggregateService<MODEL>,
    private val organizationMapper: OrganizationMapper<Organization, MODEL>,
): OrganizationQueryFeatures<MODEL>, OrganizationCommandFeatures {
    private val logger by s2.spring.utils.logger.Logger()

    override fun organizationGet(): OrganizationGetFunction<MODEL> = f2Function { query ->
        logger.info("organizationGet: $query")
        organizationFinderService.organizationGet(query, organizationMapper)
    }

    override fun organizationGetFromInsee(): OrganizationGetFromInseeFunction = f2Function { query ->
        logger.info("organizationGetFromInsee: $query")
        organizationFinderService.organizationGetFromInsee(query)
    }

    override fun organizationPage(): OrganizationPageFunction<MODEL> = f2Function { query ->
        logger.info("organizationPage: $query")
        organizationFinderService.organizationPage(query, organizationMapper)
    }

    override fun organizationRefGetAll(): OrganizationRefGetAllFunction = f2Function { query ->
        logger.info("organizationRefGetAll: $query")
        organizationFinderService.organizationRefGetAll(query)
    }

    override fun organizationCreate(): OrganizationCreateFunction = f2Function { cmd ->
        logger.info("organizationCreate: $cmd")
        organizationAggregateService.create(cmd)
    }

    override fun organizationUpdate(): OrganizationUpdateFunction = f2Function { cmd ->
        logger.info("organizationUpdate: $cmd")
        organizationAggregateService.update(cmd, organizationMapper)
    }

    suspend fun organizationUploadLogo(
        cmd: OrganizationUploadLogoCommand,
        file: org.springframework.http.codec.multipart.FilePart
    ): OrganizationUploadedLogoEvent {
        logger.info("organizationUploadLogo: $cmd")
        return organizationAggregateService.uploadLogo(cmd, file.contentByteArray())
    }

    override fun organizationDisable(): OrganizationDisableFunction = f2Function { cmd ->
        logger.info("organizationDisable: $cmd")
        organizationAggregateService.disable(cmd)
    }
}
