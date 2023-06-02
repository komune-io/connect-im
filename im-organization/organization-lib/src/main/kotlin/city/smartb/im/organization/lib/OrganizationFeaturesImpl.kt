package city.smartb.im.organization.lib

import city.smartb.im.commons.utils.contentByteArray
import city.smartb.im.organization.domain.OrganizationCommandFeatures
import city.smartb.im.organization.domain.OrganizationQueryFeatures
import city.smartb.im.organization.domain.features.command.OrganizationAddApiKeyFunction
import city.smartb.im.organization.domain.features.command.OrganizationCreateFunction
import city.smartb.im.organization.domain.features.command.OrganizationDeleteFunction
import city.smartb.im.organization.domain.features.command.OrganizationDisableFunction
import city.smartb.im.organization.domain.features.command.OrganizationRemoveApiKeyFunction
import city.smartb.im.organization.domain.features.command.OrganizationUpdateFunction
import city.smartb.im.organization.domain.features.command.OrganizationUploadLogoCommand
import city.smartb.im.organization.domain.features.command.OrganizationUploadedLogoEvent
import city.smartb.im.organization.domain.features.query.OrganizationGetFromInseeFunction
import city.smartb.im.organization.domain.features.query.OrganizationGetFunction
import city.smartb.im.organization.domain.features.query.OrganizationPageFunction
import city.smartb.im.organization.domain.features.query.OrganizationRefListFunction
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
        logger.debug("organizationGet: $query")
        organizationFinderService.organizationGet(query, organizationMapper)
    }

    override fun organizationGetFromInsee(): OrganizationGetFromInseeFunction = f2Function { query ->
        logger.debug("organizationGetFromInsee: $query")
        organizationFinderService.organizationGetFromInsee(query)
    }

    override fun organizationPage(): OrganizationPageFunction<MODEL> = f2Function { query ->
        logger.debug("organizationPage: $query")
        organizationFinderService.organizationPage(query, organizationMapper)
    }

    override fun organizationRefList(): OrganizationRefListFunction = f2Function { query ->
        logger.debug("organizationRefList: $query")
        organizationFinderService.organizationRefList(query)
    }

    override fun organizationCreate(): OrganizationCreateFunction = f2Function { cmd ->
        logger.debug("organizationCreate: $cmd")
        organizationAggregateService.create(cmd, organizationMapper)
    }

    override fun organizationUpdate(): OrganizationUpdateFunction = f2Function { cmd ->
        logger.debug("organizationUpdate: $cmd")
        organizationAggregateService.update(cmd, organizationMapper)
    }

    override fun organizationAddApiKey(): OrganizationAddApiKeyFunction = f2Function { cmd ->
        logger.debug("organizationAddApiKey: $cmd")
        organizationAggregateService.addApiKey(cmd, organizationMapper)
    }

    override fun organizationRemoveApiKey(): OrganizationRemoveApiKeyFunction = f2Function { cmd ->
        logger.debug("organizationRemoveApiKey: $cmd")
        organizationAggregateService.removeApiKey(cmd, organizationMapper)
    }

    suspend fun organizationUploadLogo(
        cmd: OrganizationUploadLogoCommand,
        file: org.springframework.http.codec.multipart.FilePart
    ): OrganizationUploadedLogoEvent {
        logger.debug("organizationUploadLogo: $cmd")
        return organizationAggregateService.uploadLogo(cmd, file.contentByteArray())
    }

    override fun organizationDisable(): OrganizationDisableFunction = f2Function { cmd ->
        logger.debug("organizationDisable: $cmd")
        organizationAggregateService.disable(cmd, organizationMapper)
    }

    override fun organizationDelete(): OrganizationDeleteFunction = f2Function { cmd ->
        logger.debug("organizationDelete: $cmd")
        organizationAggregateService.delete(cmd)
    }
}
