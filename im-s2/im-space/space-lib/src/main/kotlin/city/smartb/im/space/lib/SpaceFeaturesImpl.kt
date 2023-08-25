package city.smartb.im.space.lib

import city.smartb.im.space.domain.SpaceCommandFeatures
import city.smartb.im.space.domain.SpaceQueryFeatures
import city.smartb.im.space.domain.features.command.SpaceCreateFunction
import city.smartb.im.space.domain.features.command.SpaceDeleteFunction
import city.smartb.im.space.domain.features.query.SpaceGetFunction
import city.smartb.im.space.domain.features.query.SpacePageFunction
import city.smartb.im.space.lib.service.SpaceAggregateService
import city.smartb.im.space.lib.service.SpaceFinderService
import f2.dsl.fnc.f2Function
import org.springframework.stereotype.Service

@Service
class SpaceFeaturesImpl(
    private val spaceFinderService: SpaceFinderService,
    private val spaceAggregateService: SpaceAggregateService,
): SpaceQueryFeatures, SpaceCommandFeatures {
    private val logger by s2.spring.utils.logger.Logger()

    override fun spaceGet(): SpaceGetFunction = f2Function { query ->
        logger.debug("spaceGet: $query")
        spaceFinderService.spaceGet(query)
    }

    override fun spacePage(): SpacePageFunction = f2Function { query ->
        logger.debug("spacePage: $query")
        spaceFinderService.spacePage(query)
    }

    override fun spaceCreate(): SpaceCreateFunction = f2Function { cmd ->
        logger.debug("spaceCreate: $cmd")
        spaceAggregateService.create(cmd)
    }

    override fun spaceDelete(): SpaceDeleteFunction = f2Function { cmd ->
        logger.debug("spaceDelete: $cmd")
        spaceAggregateService.delete(cmd)
    }
}
