package city.smartb.im.space.api

import city.smartb.im.commons.auth.policies.f2Function
import city.smartb.im.space.api.service.SpacePoliciesEnforcer
import city.smartb.im.space.domain.SpaceApi
import city.smartb.im.space.domain.features.command.SpaceCreateFunction
import city.smartb.im.space.domain.features.command.SpaceDeleteFunction
import city.smartb.im.space.domain.features.query.SpaceGetFunction
import city.smartb.im.space.domain.features.query.SpaceGetResult
import city.smartb.im.space.domain.features.query.SpacePageFunction
import city.smartb.im.space.lib.SpaceAggregateService
import city.smartb.im.space.lib.SpaceFinderService
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

/**
 * @d2 service
 * @parent [city.smartb.im.space.domain.D2SpacePage]
 */
@Service
class SpaceEndpoint(
    private val spaceAggregateService: SpaceAggregateService,
    private val spaceFinderService: SpaceFinderService,
    private val spacePoliciesEnforcer: SpacePoliciesEnforcer,
): SpaceApi {
    private val logger by Logger()

    @Bean
    override fun spaceGet(): SpaceGetFunction = f2Function { query ->
        logger.info("spaceGet: $query")
        spacePoliciesEnforcer.checkGet(query.id)
        spaceFinderService.getOrNull(query.id).let(::SpaceGetResult)
    }

    @Bean
    override fun spacePage(): SpacePageFunction = f2Function { query ->
        logger.info("spacePage: $query")
        spacePoliciesEnforcer.checkPage()
        spaceFinderService.page(
            search = query.search,
            page = query.page,
            size = query.size
        )
    }

    @Bean
    override fun spaceCreate(): SpaceCreateFunction = f2Function { command ->
        logger.info("spaceCreate: $command")
        spacePoliciesEnforcer.checkCreate()
        spaceAggregateService.create(command)
    }

    @Bean
    override fun spaceDelete(): SpaceDeleteFunction = f2Function { command ->
        logger.info("spaceDelete: $command")
        spacePoliciesEnforcer.checkDelete(command.id)
        spaceAggregateService.delete(command)
    }

}
