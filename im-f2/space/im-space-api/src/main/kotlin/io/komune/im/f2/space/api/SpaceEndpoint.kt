package io.komune.im.f2.space.api

import io.komune.im.commons.auth.policies.f2Function
import io.komune.im.f2.space.api.service.SpacePoliciesEnforcer
import io.komune.im.f2.space.domain.SpaceApi
import io.komune.im.f2.space.domain.command.SpaceDefineFunction
import io.komune.im.f2.space.domain.command.SpaceDeleteFunction
import io.komune.im.f2.space.domain.query.SpaceGetFunction
import io.komune.im.f2.space.domain.query.SpaceGetResult
import io.komune.im.f2.space.domain.query.SpacePageFunction
import io.komune.im.f2.space.lib.SpaceAggregateService
import io.komune.im.f2.space.lib.SpaceFinderService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

/**
 * @d2 service
 * @parent [io.komune.im.space.domain.D2SpacePage]
 */
@Service
class SpaceEndpoint(
    private val spaceAggregateService: SpaceAggregateService,
    private val spaceFinderService: SpaceFinderService,
    private val spacePoliciesEnforcer: SpacePoliciesEnforcer,
): SpaceApi {
    private val logger = LoggerFactory.getLogger(SpaceEndpoint::class.java)

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
    override fun spaceDefine(): SpaceDefineFunction = f2Function { command ->
        logger.info("spaceDefine: $command")
        spacePoliciesEnforcer.checkDefine()
        spaceAggregateService.define(command)
    }

    @Bean
    override fun spaceDelete(): SpaceDeleteFunction = f2Function { command ->
        logger.info("spaceDelete: $command")
        spacePoliciesEnforcer.checkDelete()
        spaceAggregateService.delete(command)
    }

}
