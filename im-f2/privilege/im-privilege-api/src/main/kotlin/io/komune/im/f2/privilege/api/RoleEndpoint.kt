package io.komune.im.f2.privilege.api

import io.komune.im.commons.auth.policies.f2Function
import io.komune.im.core.privilege.domain.model.RoleTarget
import io.komune.im.f2.privilege.api.service.PrivilegePoliciesEnforcer
import io.komune.im.f2.privilege.domain.RoleApi
import io.komune.im.f2.privilege.domain.role.command.RoleDefineFunction
import io.komune.im.f2.privilege.domain.role.query.RoleGetFunction
import io.komune.im.f2.privilege.domain.role.query.RoleGetResult
import io.komune.im.f2.privilege.domain.role.query.RoleListFunction
import io.komune.im.f2.privilege.domain.role.query.RoleListResult
import io.komune.im.f2.privilege.lib.PrivilegeAggregateService
import io.komune.im.f2.privilege.lib.PrivilegeFinderService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleEndpoint(
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService,
    private val privilegePoliciesEnforcer: PrivilegePoliciesEnforcer
): RoleApi {
    private val logger = LoggerFactory.getLogger(RoleEndpoint::class.java)

    @Bean
    override fun roleGet(): RoleGetFunction = f2Function { query ->
        logger.info("roleGet: $query")
        privilegePoliciesEnforcer.checkGet()
        privilegeFinderService.getRoleOrNull(query.identifier)
            .let(::RoleGetResult)
    }

    @Bean
    override fun roleList(): RoleListFunction = f2Function { query ->
        logger.info("roleList: $query")
        privilegePoliciesEnforcer.checkList()
        privilegeFinderService.listRoles(
            targets = query.target?.let { listOf(RoleTarget.valueOf(it)) }
        ).let(::RoleListResult)
    }

    @Bean
    override fun roleDefine(): RoleDefineFunction = f2Function { command ->
        logger.info("roleDefine: $command")
        privilegePoliciesEnforcer.checkDefine()
        privilegeAggregateService.define(command)
    }
}
