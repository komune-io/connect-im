package io.komune.im.f2.privilege.api

import f2.dsl.fnc.f2Function
import io.komune.im.f2.privilege.api.service.PrivilegePoliciesEnforcer
import io.komune.im.f2.privilege.domain.FeatureApi
import io.komune.im.f2.privilege.domain.feature.command.FeatureDefineFunction
import io.komune.im.f2.privilege.domain.feature.query.FeatureGetFunction
import io.komune.im.f2.privilege.domain.feature.query.FeatureGetResult
import io.komune.im.f2.privilege.domain.feature.query.FeatureListFunction
import io.komune.im.f2.privilege.domain.feature.query.FeatureListResult
import io.komune.im.f2.privilege.lib.PrivilegeAggregateService
import io.komune.im.f2.privilege.lib.PrivilegeFinderService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeatureEndpoint(
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService,
    private val privilegePoliciesEnforcer: PrivilegePoliciesEnforcer
): FeatureApi {
    private val logger = LoggerFactory.getLogger(FeatureEndpoint::class.java)

    @Bean
    override fun featureGet(): FeatureGetFunction = f2Function { query ->
        logger.info("featureGet: $query")
        privilegePoliciesEnforcer.checkGet()
        privilegeFinderService.getFeatureOrNull(query.identifier)
            .let(::FeatureGetResult)
    }

    @Bean
    override fun featureList(): FeatureListFunction = f2Function { query ->
        logger.info("featureList: $query")
        privilegePoliciesEnforcer.checkList()
        privilegeFinderService.listFeatures().let(::FeatureListResult)
    }

    @Bean
    override fun featureDefine(): FeatureDefineFunction = f2Function { command ->
        logger.info("featureDefine: $command")
        privilegePoliciesEnforcer.checkDefine()
        privilegeAggregateService.define(command)
    }
}
