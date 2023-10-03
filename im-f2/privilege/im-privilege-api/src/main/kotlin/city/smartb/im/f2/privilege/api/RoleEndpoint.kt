package city.smartb.im.f2.privilege.api

import city.smartb.im.commons.auth.policies.f2Function
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.f2.privilege.api.service.PrivilegePoliciesEnforcer
import city.smartb.im.f2.privilege.domain.RoleApi
import city.smartb.im.f2.privilege.domain.role.command.RoleDefineFunction
import city.smartb.im.f2.privilege.domain.role.query.RoleGetFunction
import city.smartb.im.f2.privilege.domain.role.query.RoleGetResult
import city.smartb.im.f2.privilege.domain.role.query.RoleListFunction
import city.smartb.im.f2.privilege.domain.role.query.RoleListResult
import city.smartb.im.f2.privilege.lib.PrivilegeAggregateService
import city.smartb.im.f2.privilege.lib.PrivilegeFinderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class RoleEndpoint(
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService,
    private val privilegePoliciesEnforcer: PrivilegePoliciesEnforcer
): RoleApi {
    private val logger by Logger()

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
